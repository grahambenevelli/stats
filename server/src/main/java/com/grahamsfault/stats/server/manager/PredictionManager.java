package com.grahamsfault.stats.server.manager;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.prediction.util.similarity.impl.EuclideanCalculator;
import com.grahamsfault.prediction.util.similarity.impl.PearsonCalculator;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.impl.Tuple;
import com.grahamsfault.stats.server.command.prediction.impl.helper.PriorityListGenerator;
import com.grahamsfault.stats.server.command.prediction.impl.helper.StdDevStatsHelper;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.command.prediction.model.NormalizedStats;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.dao.PredictionDAO;
import com.grahamsfault.stats.server.model.NClosestResults;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The manager for handling predictions
 */
public class PredictionManager {

	private final PredictionDAO predictionDAO;
	private final StatsManager statsManager;
	private final ImportManager importManager;

	public PredictionManager(PredictionDAO predictionDAO, StatsManager statsManager, ImportManager importManager) {
		this.predictionDAO = predictionDAO;
		this.statsManager = statsManager;
		this.importManager = importManager;
	}

	/**
	 * Record the given results of each position
	 *
	 * @param name    The name of the algorithm
	 * @param results The results of a run of that algorithm
	 */
	public void recordResults(final String name, final PredictionResults results) {
		List<Position> list = Arrays.asList(Position.values());
		list.forEach(position -> recordResults(name, position, results.getStats(position)));
	}

	/**
	 * Record the given results of each position
	 *
	 * @param name     The name of the algorithm
	 * @param position The position the stats are for
	 * @param stats    The accuracy stats
	 */
	private void recordResults(String name, Position position, AccuracyStats stats) {
		try {
			predictionDAO.recordTestRunAccuracy(name, position, stats);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<NClosestResults> nClosest(Player player, int year, int n) {
		Optional<PlayerStats> playerYearlyStats = statsManager.getPlayerYearlyStats(player, year);
		if (!playerYearlyStats.isPresent()) {
			return Optional.empty();
		}

		NormalizedStats normalizedPlayerStats = normalizeStats(playerYearlyStats.get(), StdDevStatsHelper.instance(statsManager).getStdDevStats(player.getPosition()));
		PriorityListGenerator<NClosestResults.Unit> priorityListGenerator = new PriorityListGenerator<>(n + 1);

		importManager.getYears()
				.stream()
				.filter(integer -> integer <= year)
				.forEach(predictionYear -> {
					Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> statsMap = getNormalizedStatsForComparison(player, predictionYear);
					calc(new PearsonCalculator(), priorityListGenerator, statsMap, normalizedPlayerStats);
				});

		List<NClosestResults.Unit> build = priorityListGenerator.build().stream()
				.skip(1)
				.collect(Collectors.toList());

		return Optional.of(new NClosestResults(n, build, player));
	}

	private void calc(CorrelationCalculator correlationCalculator, PriorityListGenerator<NClosestResults.Unit> priorityListGenerator, Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> statsMap, NormalizedStats normalizedPlayerStats) {
		statsMap.entrySet()
				.stream()
				.forEach(tupleTupleEntry -> {
					Tuple<Player, Year> key = tupleTupleEntry.getKey();
					Tuple<NormalizedStats, PlayerStats> value = tupleTupleEntry.getValue();

					double correlation = calculateCorrelation(correlationCalculator, value.getFirst(), normalizedPlayerStats);
					priorityListGenerator.add(
							correlation,
							NClosestResults.unit(
									key.getFirst(),
									key.getSecond().intValue(),
									value.getSecond(),
									correlation
							)
					);
				});
	}

	public Optional<List<PlayerStats>> nClosestStatsPrediction(CorrelationCalculator correlationCalculator, Player player, int year, int n) {
		Optional<PlayerStats> playerYearlyStats = statsManager.getPlayerYearlyStats(player, year);
		if (!playerYearlyStats.isPresent()) {
			return Optional.empty();
		}

		NormalizedStats normalizedPlayerStats = normalizeStats(playerYearlyStats.get(), StdDevStatsHelper.instance(statsManager).getStdDevStats(player.getPosition()));
		PriorityListGenerator<PlayerStats> priorityListGenerator = new PriorityListGenerator<>(n);

		importManager.getYears()
				.stream()
				.filter(integer -> integer <= year)
				.forEach(predictionYear -> {
					Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> statsMap = getNormalizedStatsForComparison(player, predictionYear);
					processPlayerStats(correlationCalculator, priorityListGenerator, statsMap, normalizedPlayerStats);
				});

		return Optional.of(priorityListGenerator.build());
	}

	private NormalizedStats normalizeStats(PlayerStats playerStats, StdDevStats stdDevStats) {
		return NormalizedStats.builder()
				.passingAttempts(playerStats.getPassingAttempts() * stdDevStats.getPassingAttempts())
				.passingCompletions(playerStats.getPassingCompletions() * stdDevStats.getPassingCompletions())
				.passingYards(playerStats.getPassingYards() * stdDevStats.getPassingYards())
				.passingYards(playerStats.getPassingCompletions() * stdDevStats.getPassingCompletions())
				.passingTouchdowns(playerStats.getPassingTouchdowns() * stdDevStats.getPassingTouchdowns())
				.interceptions(playerStats.getInterceptions() * stdDevStats.getInterceptions())
				.rushingAttempts(playerStats.getRushingAttempts() * stdDevStats.getRushingAttempts())
				.rushingYards(playerStats.getRushingYards() * stdDevStats.getRushingYards())
				.rushingTouchdowns(playerStats.getRushingTouchdowns() * stdDevStats.getRushingTouchdowns())
				.rushingLong(playerStats.getRushingLong() * stdDevStats.getRushingLong())
				.rushingLongTouchdown(playerStats.getRushingLongTouchdown() * stdDevStats.getRushingLongTouchdown())
				.receptions(playerStats.getReceptions() * stdDevStats.getReceptions())
				.receivingYards(playerStats.getReceivingYards() * stdDevStats.getReceivingYards())
				.receivingTouchdowns(playerStats.getReceivingTouchdowns() * stdDevStats.getReceivingTouchdowns())
				.receivingLong(playerStats.getReceivingLong() * stdDevStats.getReceivingLong())
				.receivingLongTouchdown(playerStats.getReceivingLongTouchdown() * stdDevStats.getReceivingLongTouchdown())
				.fumbles(playerStats.getFumbles() * stdDevStats.getFumbles())
				.fumblesLost(playerStats.getFumblesLost() * stdDevStats.getFumblesLost())
				.fumblesRecovered(playerStats.getFumblesRecovered() * stdDevStats.getFumblesRecovered())
				.fumbleYards(playerStats.getFumbleYards() * stdDevStats.getFumbleYards())
				.build();
	}

	private Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> getNormalizedStatsForComparison(Player player, Integer yearBak) {
		Map<Tuple<Player, Year>, PlayerStats> playerStats = statsManager.getYearlyPositionStats(player.getPosition(), yearBak);

		// convert player stats to normalized std dev stats
		return playerStats.entrySet()
				.stream()
				.map(tuplePlayerStatsEntry -> {
					NormalizedStats normalizedStats1 = normalizeStats(tuplePlayerStatsEntry.getValue(), StdDevStatsHelper.instance(statsManager).getStdDevStats(player.getPosition()));
					return new Tuple<>(
							tuplePlayerStatsEntry.getKey(),
							new Tuple<>(
									normalizedStats1,
									tuplePlayerStatsEntry.getValue()
							)
					);
				})
				.collect(Collectors.toMap(Tuple::getFirst, Tuple::getSecond));
	}

	private void processPlayerStats(CorrelationCalculator correlationCalculator, PriorityListGenerator<PlayerStats> priorityListGenerator, Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> statsMap, NormalizedStats normalizedPlayerStats) {
		statsMap.entrySet()
				.stream()
				.forEach(tupleTupleEntry -> {
					Tuple<NormalizedStats, PlayerStats> value = tupleTupleEntry.getValue();

					priorityListGenerator.add(
							calculateCorrelation(correlationCalculator, value.getFirst(), normalizedPlayerStats),
							value.getSecond()
					);
				});
	}

	private double calculateCorrelation(CorrelationCalculator correlationCalculator, NormalizedStats otherStats, NormalizedStats playerStats) {
		List<Node<Double>> normalizedNodes = ImmutableList.<Node<Double>>builder()
				.add(Node.of(otherStats.getPassingAttempts()))
				.add(Node.of(otherStats.getPassingCompletions()))
				.add(Node.of(otherStats.getPassingYards()))
				.add(Node.of(otherStats.getPassingTouchdowns()))
				.add(Node.of(otherStats.getInterceptions()))
				.add(Node.of(otherStats.getRushingAttempts()))
				.add(Node.of(otherStats.getRushingYards()))
				.add(Node.of(otherStats.getRushingTouchdowns()))
				.add(Node.of(otherStats.getRushingLong()))
				.add(Node.of(otherStats.getRushingLongTouchdown()))
				.add(Node.of(otherStats.getReceptions()))
				.add(Node.of(otherStats.getReceivingYards()))
				.add(Node.of(otherStats.getReceivingTouchdowns()))
				.add(Node.of(otherStats.getReceivingLong()))
				.add(Node.of(otherStats.getReceivingLongTouchdown()))
				.add(Node.of(otherStats.getFumbles()))
				.add(Node.of(otherStats.getFumblesLost()))
				.add(Node.of(otherStats.getFumblesRecovered()))
				.add(Node.of(otherStats.getFumbleYards()))
				.build();

		List<Node<Double>> playerNodes = ImmutableList.<Node<Double>>builder()
				.add(Node.of(1.0 * playerStats.getPassingAttempts()))
				.add(Node.of(1.0 * playerStats.getPassingCompletions()))
				.add(Node.of(1.0 * playerStats.getPassingYards()))
				.add(Node.of(1.0 * playerStats.getPassingTouchdowns()))
				.add(Node.of(1.0 * playerStats.getInterceptions()))
				.add(Node.of(1.0 * playerStats.getRushingAttempts()))
				.add(Node.of(1.0 * playerStats.getRushingYards()))
				.add(Node.of(1.0 * playerStats.getRushingTouchdowns()))
				.add(Node.of(1.0 * playerStats.getRushingLong()))
				.add(Node.of(1.0 * playerStats.getRushingLongTouchdown()))
				.add(Node.of(1.0 * playerStats.getReceptions()))
				.add(Node.of(1.0 * playerStats.getReceivingYards()))
				.add(Node.of(1.0 * playerStats.getReceivingTouchdowns()))
				.add(Node.of(1.0 * playerStats.getReceivingLong()))
				.add(Node.of(1.0 * playerStats.getReceivingLongTouchdown()))
				.add(Node.of(1.0 * playerStats.getFumbles()))
				.add(Node.of(1.0 * playerStats.getFumblesLost()))
				.add(Node.of(1.0 * playerStats.getFumblesRecovered()))
				.add(Node.of(1.0 * playerStats.getFumbleYards()))
				.build();

		return correlationCalculator.calculateCorrelation(normalizedNodes, playerNodes).getValue();
	}
}
