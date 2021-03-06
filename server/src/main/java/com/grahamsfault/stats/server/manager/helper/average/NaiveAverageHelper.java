package com.grahamsfault.stats.server.manager.helper.average;

import com.google.common.collect.ImmutableMap;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.model.AverageStats;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.manager.helper.AverageHelper;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaiveAverageHelper extends AverageHelper {

	private final PlayerManager playerManager;
	private final StatsManager statsManager;

	public NaiveAverageHelper(PlayerManager playerManager, StatsManager statsManager) {
		this.playerManager = playerManager;
		this.statsManager = statsManager;
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
			if (playerStats.isPresent()) {
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
