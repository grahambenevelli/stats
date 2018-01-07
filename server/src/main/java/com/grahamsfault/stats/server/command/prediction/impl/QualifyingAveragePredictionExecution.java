package com.grahamsfault.stats.server.command.prediction.impl;

import com.grahamsfault.stats.server.api.model.Player;
import com.grahamsfault.stats.server.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.manager.helper.QualifyingNumbersHelper;
import com.grahamsfault.stats.server.manager.helper.average.NaiveAverageHelper;
import com.grahamsfault.stats.server.manager.helper.average.QualifyingAverageHelper;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Prediction algorithm that takes the average of qualifying players and uses that
 */
public class QualifyingAveragePredictionExecution extends PredictionExecution {

	private final ImportManager importManager;
	private final StatsManager statsManager;
	private final PlayerManager playerManager;
	private final QualifyingNumbersHelper qualifyingNumbersHelper;
	private final NaiveAverageHelper naiveAverageHelper;
	private final QualifyingAverageHelper qualifyingAverageHelper;;

	public QualifyingAveragePredictionExecution(
			ImportManager importManager,
			StatsManager statsManager,
			PlayerManager playerManager,
			QualifyingNumbersHelper qualifyingNumbersHelper,
			NaiveAverageHelper naiveAverageHelper,
			QualifyingAverageHelper qualifyingAverageHelper) {
		super("qualifying-average");
		this.importManager = importManager;
		this.statsManager = statsManager;
		this.playerManager = playerManager;
		this.qualifyingNumbersHelper = qualifyingNumbersHelper;
		this.naiveAverageHelper = naiveAverageHelper;
		this.qualifyingAverageHelper = qualifyingAverageHelper;
	}

	@Override
	public PredictionResults run() {
		PredictionResults.Builder predictionBuilder = PredictionResults.builder();

		for (Integer year : getPredictionYears()) {
			Map<Position, AverageStats> averageStatsPerPosition = naiveAverageHelper.getAveragePerPosition(year - 1);
			Map<Position, AverageStats> averageStatsQualifying = qualifyingAverageHelper.getAveragePerPosition(year - 1);

			for (Player player : playerManager.getPlayersPerYear(year - 1)) {
				Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
				if (playerStats.isPresent()) {
					if (qualifyingNumbersHelper.qualifies(player.getPosition(), statsManager.getPlayerYearlyStats(player, year - 1))) {
						predictionBuilder.increment(player.getPosition(), playerStats.get(), averageStatsQualifying.get(player.getPosition()));
					} else {
						predictionBuilder.increment(player.getPosition(), playerStats.get(), averageStatsPerPosition.get(player.getPosition()));
					}
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
