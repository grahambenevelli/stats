package com.grahamsfault.stats.server.command.prediction.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.impl.helper.PriorityListGenerator;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.command.prediction.model.NormalizedStats;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.Arrays;
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
	private final PlayerManager playerManager;
	private final ImportManager importManager;
	private final StatsManager statsManager;

	public NClosestPredictionExecution(
			int n,
			CorrelationCalculator correlationCalculator,
			PlayerManager playerManager,
			ImportManager importManager,
			StatsManager statsManager) {
		super("average-" + n + "-closest-" + correlationCalculator.getClass().getSimpleName());
		this.n = n;
		this.correlationCalculator = correlationCalculator;
		this.playerManager = playerManager;
		this.importManager = importManager;
		this.statsManager = statsManager;
	}

	@Override
	public PredictionResults run() {
		// Still needs to work this out, only an initial cut
		PredictionResults.Builder predictionBuilder = PredictionResults.builder();
		Map<Position, StdDevStats> stdDevStatsMap = getStdDevStatsMap();

		for (Integer year : getPredictionYears()) {
			for (Player player : playerManager.getPlayersPerYear(year - 1)) {
				List<PlayerStats> closestStats = getNClosest(player, year - 1, stdDevStatsMap.get(player.getPosition()));

				Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
				if (playerStats.isPresent()) {
					predictionBuilder.increment(player.getPosition(), playerStats.get(), getGuess(closestStats));
				}
			}
		}

		return predictionBuilder.build();
	}

	private Map<Position, StdDevStats> getStdDevStatsMap() {
		ImmutableMap.Builder<Position, StdDevStats> builder = ImmutableMap.builder();
		Arrays.asList(Position.values()).stream()
				.forEach(position -> builder.put(position, getStdDevStatsForPostion(position)));

		return builder.build();
	}

	private StdDevStats getStdDevStatsForPostion(Position position) {
		return statsManager.getStdDevForPosition(position);
	}

	private AverageStats getGuess(List<PlayerStats> closestStats) {
		AverageStats.Builder builder = AverageStats.builder();
		closestStats.forEach(builder::incrementStats);
		return builder.build();
	}

	private List<PlayerStats> getNClosest(Player player, int year, StdDevStats stdDevStats) {
		Optional<PlayerStats> playerYearlyStats = statsManager.getPlayerYearlyStats(player, year);
		if (!playerYearlyStats.isPresent()) {
			throw new IllegalArgumentException();
		}

		PriorityListGenerator<PlayerStats> priorityListGenerator = new PriorityListGenerator<>(n);

		List<Integer> years = importManager.getYears()
				.stream()
				.filter(integer -> integer <= year)
				.collect(Collectors.toList());

		for (Integer yearBak : years) {
			Map<Tuple<Player, Year>, PlayerStats> playerStats = statsManager.getYearlyPositionStats(player.getPosition(), yearBak);

			// convert player stats to std dev stats
			Map<Tuple<Player, Year>, NormalizedStats> correlationCalculator = playerStats.entrySet()
					.stream()
					.map(tuplePlayerStatsEntry -> {
						NormalizedStats normalizedStats1 = normalizeStats(tuplePlayerStatsEntry.getValue(), stdDevStats);
						return Maps.immutableEntry(tuplePlayerStatsEntry.getKey(), normalizedStats1);
					})
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

			correlationCalculator.entrySet()
					.stream()
					.forEach(normalizedStats -> playerStats.entrySet()
							.stream()
							.forEach(playerStats1 -> priorityListGenerator.add(
									calculateCorrelation(normalizedStats.getValue(), playerStats1.getValue()),
									playerStats1.getValue()
							)));
		}

		return priorityListGenerator.build();
	}

	private double calculateCorrelation(NormalizedStats normalizedStats, PlayerStats playerStats) {
		List<Node<Double>> normalizedNodes = ImmutableList.<Node<Double>>builder()
				.add(Node.of(normalizedStats.getPassingAttempts()))
				.add(Node.of(normalizedStats.getPassingCompletions()))
				.add(Node.of(normalizedStats.getPassingYards()))
				.add(Node.of(normalizedStats.getPassingTouchdowns()))
				.add(Node.of(normalizedStats.getInterceptions()))
				.add(Node.of(normalizedStats.getRushingAttempts()))
				.add(Node.of(normalizedStats.getRushingYards()))
				.add(Node.of(normalizedStats.getRushingTouchdowns()))
				.add(Node.of(normalizedStats.getRushingLong()))
				.add(Node.of(normalizedStats.getRushingLongTouchdown()))
				.add(Node.of(normalizedStats.getReceptions()))
				.add(Node.of(normalizedStats.getReceivingYards()))
				.add(Node.of(normalizedStats.getReceivingTouchdowns()))
				.add(Node.of(normalizedStats.getReceivingLong()))
				.add(Node.of(normalizedStats.getReceivingLongTouchdown()))
				.add(Node.of(normalizedStats.getFumbles()))
				.add(Node.of(normalizedStats.getFumblesLost()))
				.add(Node.of(normalizedStats.getFumblesRecovered()))
				.add(Node.of(normalizedStats.getFumbleYards()))
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
				.build();
	}


	/**
	 * Get the prediction years for this execution
	 *
	 * @return The prediction years
	 */
	private List<Integer> getPredictionYears() {
		return importManager.getYears()
				.stream()
				.skip(1)
				.collect(Collectors.toList());
	}

}
