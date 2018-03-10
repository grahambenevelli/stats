package com.grahamsfault.stats.server.command.prediction.impl;

import com.google.common.collect.Maps;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.manager.helper.average.NaiveAverageHelper;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaiveAverageOnlyPredictionExecution extends PredictionExecution {

	private final StatsManager statsManager;
	private final NaiveAverageHelper averageHelper;
	private final Map<Year, Map<Position, AverageStats>> averageStats;

	public NaiveAverageOnlyPredictionExecution(ImportManager importManager, StatsManager statsManager, PlayerManager playerManager, NaiveAverageHelper averageHelper) {
		super("average-only", importManager, playerManager);
		this.statsManager = statsManager;
		this.averageHelper = averageHelper;

		averageStats = Arrays.asList(Year.values()).stream()
				.collect(
						Collectors.toMap(year -> year, year -> Maps.newHashMap())
				);
	}

	@Override
	protected Optional<PredictionResults.Unit> entry(Player player, Integer year) {
		Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
		if (playerStats.isPresent()) {
			AverageStats guessStats = getAveragePerPosition(Year.fromIntValue(year - 1), player.getPosition());
			return Optional.of(
					PredictionResults.unit(player.getPosition(), playerStats.get(), guessStats)
			);
		}

		return Optional.empty();
	}

	/**
	 * Get the average stats per position and year
	 *
	 * @param year The year to average
	 * @param position The position to average
	 * @return The average stats
	 */
	private AverageStats getAveragePerPosition(Year year, Position position) {
		if (averageStats.get(year).isEmpty()) {
			averageStats.put(year, averageHelper.getAveragePerPosition(year.intValue()));
		}

		return averageStats.get(year)
				.get(position);
	}

}
