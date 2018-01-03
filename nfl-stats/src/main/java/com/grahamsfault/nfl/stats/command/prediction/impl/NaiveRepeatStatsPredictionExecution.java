package com.grahamsfault.nfl.stats.command.prediction.impl;

import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.command.prediction.PredictionExecution;
import com.grahamsfault.nfl.stats.command.prediction.PredictionResults;
import com.grahamsfault.nfl.stats.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.stats.manager.ImportManager;
import com.grahamsfault.nfl.stats.manager.PlayerManager;
import com.grahamsfault.nfl.stats.manager.StatsManager;
import com.grahamsfault.nfl.stats.model.GuessStats;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaiveRepeatStatsPredictionExecution extends PredictionExecution {

	private final ImportManager importManager;
	private final StatsManager statsManager;
	private final PlayerManager playerManager;

	public NaiveRepeatStatsPredictionExecution(ImportManager importManager, StatsManager statsManager, PlayerManager playerManager) {
		super("repeat-stats");
		this.importManager = importManager;
		this.statsManager = statsManager;
		this.playerManager = playerManager;
	}

	@Override
	public PredictionResults run() {
		PredictionResults.Builder predictionBuilder = PredictionResults.builder();

		for (Integer year : getPredictionYears()) {
			for (Player player : playerManager.getPlayersPerYear(year - 1)) {
				Optional<GuessStats> predictionStats = convertToGuessStats(statsManager.getPlayerYearlyStats(player, year - 1));
				Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
				if (playerStats.isPresent()) {
					if (predictionStats.isPresent()) {
						predictionBuilder.increment(player.getPosition(), playerStats.get(), predictionStats.get());
					} else {
						predictionBuilder.increment(player.getPosition(), playerStats.get(), zeroedStats());
					}
				}
			}
		}

		return predictionBuilder.build();
	}

	private GuessStats zeroedStats() {
		return AverageStats.builder().build();
	}

	private Optional<GuessStats> convertToGuessStats(Optional<PlayerStats> playerStats) {
		if (!playerStats.isPresent()) {
			return Optional.empty();
		}

		GuessStats guessStats = AverageStats.builder()
				.incrementPlayersRecieved()
				.incrementStats(playerStats.get())
				.build();

		return Optional.of(guessStats);
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
