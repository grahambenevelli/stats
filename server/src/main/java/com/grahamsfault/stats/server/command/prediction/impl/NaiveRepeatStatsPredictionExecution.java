package com.grahamsfault.stats.server.command.prediction.impl;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.model.GuessStats;
import com.grahamsfault.stats.server.model.PlayerStats;

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
			for (Player player : playerManager.getQualifyingPlayersForYear(year - 1)) {
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

	@Override
	protected Optional<PredictionResults.Unit> entry(Player player, Integer year) {
		throw new RuntimeException();
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

}
