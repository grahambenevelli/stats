package com.grahamsfault.stats.server.manager;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.impl.Tuple;
import com.grahamsfault.stats.server.command.prediction.impl.helper.PriorityListGenerator;
import com.grahamsfault.stats.server.command.prediction.impl.helper.StdDevStatsHelper;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.command.prediction.model.NormalizedStats;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.dao.PredictionDAO;
import com.grahamsfault.stats.server.manager.helper.PlayerCorrelationCalculator;
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
	public void recordResults(final String name, String description, final PredictionResults results) {
		List<Position> list = Arrays.asList(Position.values());
		list.forEach(position -> recordResults(name, description, position, results.getStats(position)));
	}

	/**
	 * Record the given results of each position
	 *
	 * @param name     The name of the algorithm
	 * @param position The position the stats are for
	 * @param stats    The accuracy stats
	 */
	private void recordResults(String name, String description, Position position, AccuracyStats stats) {
		try {
			predictionDAO.recordTestRunAccuracy(name, description, position, stats);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Optional<NClosestResults> nClosest(PlayerCorrelationCalculator playerCorrelationCalculator, Player player, int year, int n) {
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
					calc(playerCorrelationCalculator, priorityListGenerator, statsMap, normalizedPlayerStats);
				});

		List<NClosestResults.Unit> build = priorityListGenerator.build().stream()
				.skip(1)
				.collect(Collectors.toList());

		return Optional.of(new NClosestResults(n, build, player));
	}

	private void calc(PlayerCorrelationCalculator playerCorrelationCalculator, PriorityListGenerator<NClosestResults.Unit> priorityListGenerator, Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> statsMap, NormalizedStats normalizedPlayerStats) {
		statsMap.entrySet()
				.stream()
				.forEach(tupleTupleEntry -> {
					Tuple<Player, Year> key = tupleTupleEntry.getKey();
					Tuple<NormalizedStats, PlayerStats> value = tupleTupleEntry.getValue();

					double correlation = calculateCorrelation(playerCorrelationCalculator, value.getFirst(), normalizedPlayerStats);
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

	public Optional<List<PlayerStats>> nClosestStatsPrediction(PlayerCorrelationCalculator playerCorrelationCalculator, Player player, int year, int n) {
		Optional<NClosestResults> nClosestResults = this.nClosest(playerCorrelationCalculator, player, year, n);
		if (!nClosestResults.isPresent()) {
			return Optional.empty();
		}

		List<PlayerStats> list = nClosestResults.get().getClosest().stream()
				.map(unit -> statsManager.getPlayerYearlyStats(unit.getPlayer(), unit.getYear() + 1))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		return Optional.of(list);
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

	private double calculateCorrelation(PlayerCorrelationCalculator playerCorrelationCalculator, NormalizedStats otherStats, NormalizedStats playerStats) {
		return playerCorrelationCalculator
				.calculateCorrelation(otherStats, playerStats);
	}
}
