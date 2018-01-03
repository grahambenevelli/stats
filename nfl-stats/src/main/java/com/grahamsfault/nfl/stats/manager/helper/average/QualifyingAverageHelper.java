package com.grahamsfault.nfl.stats.manager.helper.average;

import com.google.common.collect.ImmutableMap;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.api.model.player.Position;
import com.grahamsfault.nfl.stats.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.stats.manager.PlayerManager;
import com.grahamsfault.nfl.stats.manager.StatsManager;
import com.grahamsfault.nfl.stats.manager.helper.AverageHelper;
import com.grahamsfault.nfl.stats.manager.helper.QualifyingNumbersHelper;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QualifyingAverageHelper extends AverageHelper {

	private final PlayerManager playerManager;
	private final StatsManager statsManager;
	private final QualifyingNumbersHelper qualifyingNumbersHelper;

	public QualifyingAverageHelper(PlayerManager playerManager, StatsManager statsManager, QualifyingNumbersHelper qualifyingNumbersHelper) {
		this.playerManager = playerManager;
		this.statsManager = statsManager;
		this.qualifyingNumbersHelper = qualifyingNumbersHelper;
	}

	@Override
	public Map<Position, AverageStats> getAveragePerPosition(Integer year) {
		Map<Position, AverageStats.Builder> averageStatsBuilderPerPosition = ImmutableMap.<Position, AverageStats.Builder>builder()
				.put(Position.QB, AverageStats.builder())
				.put(Position.RB, AverageStats.builder())
				.put(Position.WR, AverageStats.builder())
				.put(Position.TE, AverageStats.builder())
				.build();

		for (Player player : playerManager.getPlayersPerYear(year)) {
			Optional<PlayerStats> playerStats = statsManager.getPlayerYearlyStats(player, year);
			if (playerStats.isPresent() && qualifyingNumbersHelper.qualifies(player.getPosition(), playerStats)) {
				AverageStats.Builder builder = averageStatsBuilderPerPosition.get(player.getPosition());
				incrementAverageStats(builder, playerStats.get());
			}
		}

		return averageStatsBuilderPerPosition.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						e -> e.getValue().build()
				));
	}

}
