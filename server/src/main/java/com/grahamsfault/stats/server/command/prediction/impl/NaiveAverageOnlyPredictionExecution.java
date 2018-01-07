package com.grahamsfault.stats.server.command.prediction.impl;

import com.grahamsfault.stats.server.api.model.Player;
import com.grahamsfault.stats.server.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.manager.helper.average.NaiveAverageHelper;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaiveAverageOnlyPredictionExecution extends PredictionExecution {

	private final ImportManager importManager;
	private final StatsManager statsManager;
	private final PlayerManager playerManager;
	private final NaiveAverageHelper averageHelper;

	public NaiveAverageOnlyPredictionExecution(ImportManager importManager, StatsManager statsManager, PlayerManager playerManager, NaiveAverageHelper averageHelper) {
		super("average-only");
		this.importManager = importManager;
		this.statsManager = statsManager;
		this.playerManager = playerManager;
		this.averageHelper = averageHelper;
	}

	@Override
	public PredictionResults run() {
		PredictionResults.Builder predictionBuilder = PredictionResults.builder();

		for (Integer year : getPredictionYears()) {
			Map<Position, AverageStats> averageStatsPerPosition = averageHelper.getAveragePerPosition(year - 1);

			for (Player player : playerManager.getPlayersPerYear(year - 1)) {
				Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
				if (playerStats.isPresent()) {
					predictionBuilder.increment(player.getPosition(), playerStats.get(), averageStatsPerPosition.get(player.getPosition()));
				}
			}
		}

		return predictionBuilder.build();
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
