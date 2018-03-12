package com.grahamsfault.stats.server.command.prediction.impl;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.PredictionManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.List;
import java.util.Optional;

/**
 * Prediction algorithm that takes the average of qualifying players and uses that
 */
public class NClosestPredictionExecution extends PredictionExecution {

	private final int n;
	private final CorrelationCalculator correlationCalculator;
	private final StatsManager statsManager;
	private final PredictionManager predictionManager;

	public NClosestPredictionExecution(
			int n,
			CorrelationCalculator correlationCalculator,
			PlayerManager playerManager,
			ImportManager importManager,
			StatsManager statsManager,
			PredictionManager predictionManager) {
		super("average-" + n + "-closest-" + correlationCalculator.getClass().getSimpleName(), importManager, playerManager);
		this.n = n;
		this.correlationCalculator = correlationCalculator;
		this.statsManager = statsManager;
		this.predictionManager = predictionManager;
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
		return predictionManager.nClosestStatsPrediction(correlationCalculator, player, year, n);
	}

}
