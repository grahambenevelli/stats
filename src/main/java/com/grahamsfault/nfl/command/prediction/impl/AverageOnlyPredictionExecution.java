package com.grahamsfault.nfl.command.prediction.impl;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.PredictionExecution;
import com.grahamsfault.nfl.command.prediction.PredictionResults;
import com.grahamsfault.nfl.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.manager.ImportManager;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.manager.helper.AverageHelper;
import com.grahamsfault.nfl.model.PlayerStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AverageOnlyPredictionExecution extends PredictionExecution {

	private final ImportManager importManager;
	private final StatsManager statsManager;
	private final PlayerManager playerManager;
	private final AverageHelper averageHelper;

	public AverageOnlyPredictionExecution(ImportManager importManager, StatsManager statsManager, PlayerManager playerManager, AverageHelper averageHelper) {
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
