package com.grahamsfault.stats.server.command.prediction.impl;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.impl.helper.PriorityListGenerator;
import com.grahamsfault.stats.server.command.prediction.impl.helper.StdDevStatsHelper;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.command.prediction.model.NormalizedStats;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Prediction algorithm that takes the average of qualifying players and uses that
 */
public class NClosestPredictionExecution extends PredictionExecution {

	private final int n;
	private final CorrelationCalculator correlationCalculator;
	private final StatsManager statsManager;

	public NClosestPredictionExecution(
			int n,
			CorrelationCalculator correlationCalculator,
			PlayerManager playerManager,
			ImportManager importManager,
			StatsManager statsManager) {
		super("average-" + n + "-closest-" + correlationCalculator.getClass().getSimpleName(), importManager, playerManager);
		this.n = n;
		this.correlationCalculator = correlationCalculator;
		this.statsManager = statsManager;
	}

	@Override
	protected Optional<PredictionResults.Unit> entry(Player player, Integer year) {
		Optional<List<PlayerStats>> closestStats = getNClosest(player, year - 1);

		if (closestStats.isPresent()) {
			Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
			if (playerStats.isPresent()) {
				return Optional.of(
						PredictionResults.unit(
								player.getPosition(),
								playerStats.get(),
								average(closestStats.get())
						)
				);
			}
		}

		return Optional.empty();
	}

	/**
	 * Average stats
	 *
	 * @param closestStats The n closest stats
	 * @return The average of those stats
	 */
	private AverageStats average(List<PlayerStats> closestStats) {
		AverageStats.Builder builder = AverageStats.builder();
		closestStats.forEach(playerStats -> {
			builder.incrementPlayersRecieved();
			builder.incrementStats(playerStats);
		});

		return builder.build();
	}

	private Optional<List<PlayerStats>> getNClosest(Player player, int year) {
		Optional<PlayerStats> playerYearlyStats = statsManager.getPlayerYearlyStats(player, year);
		if (!playerYearlyStats.isPresent()) {
			return Optional.empty();
		}

		NormalizedStats normalizedPlayerStats = normalizeStats(playerYearlyStats.get(), StdDevStatsHelper.instance(statsManager).getStdDevStats(player.getPosition()));
		PriorityListGenerator<PlayerStats> priorityListGenerator = new PriorityListGenerator<>(n);

		List<Integer> years = importManager.getYears()
				.stream()
				.filter(integer -> integer <= year)
				.collect(Collectors.toList());

		for (Integer yearBak : years) {
			Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> correlationCalculator = getNormalizedStatsForComparison(player, yearBak);
			processPlayerStats(priorityListGenerator, correlationCalculator, normalizedPlayerStats);
		}

		return Optional.of(priorityListGenerator.build());
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

	private void processPlayerStats(PriorityListGenerator<PlayerStats> priorityListGenerator, Map<Tuple<Player, Year>, Tuple<NormalizedStats, PlayerStats>> correlationCalculator, NormalizedStats normalizedPlayerStats) {
		correlationCalculator.entrySet()
				.stream()
				.forEach(tupleTupleEntry -> {
					Tuple<NormalizedStats, PlayerStats> value = tupleTupleEntry.getValue();

					priorityListGenerator.add(
							calculateCorrelation(value.getFirst(), normalizedPlayerStats),
							value.getSecond()
					);
				});
	}

	private double calculateCorrelation(NormalizedStats otherStats, NormalizedStats playerStats) {
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

		return this.correlationCalculator.calculateCorrelation(normalizedNodes, playerNodes).getValue();
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

	/**
	 * Get the prediction years for this execution
	 *
	 * @return The prediction years
	 */
	protected List<Integer> getPredictionYears() {
		return importManager.getYears()
				.stream()
				.skip(1)
				.collect(Collectors.toList());
	}

}
