package com.grahamsfault.nfl.manager.helper.average;

import com.google.common.collect.ImmutableMap;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.manager.helper.AverageHelper;
import com.grahamsfault.nfl.model.PlayerStats;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaiveAverageHelper implements AverageHelper {

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

	private void incrementAverageStats(AverageStats.Builder builder, PlayerStats playerStats) {
		builder.incrementPlayersRecieved();

		builder.incrementPassingAttempts(playerStats.getPassingAttempts());
		builder.incrementPassingCompletions(playerStats.getPassingCompletions());
		builder.incrementPassingYards(playerStats.getPassingYards());
		builder.incrementPassingTouchdowns(playerStats.getPassingTouchdowns());
		builder.incrementInterceptions(playerStats.getInterceptions());
		builder.incrementRushingAttempts(playerStats.getRushingAttempts());
		builder.incrementRushingYards(playerStats.getRushingYards());
		builder.incrementRushingTouchdowns(playerStats.getRushingTouchdowns());
		builder.incrementRushingLong(playerStats.getRushingLong());
		builder.incrementRushingLongTouchdown(playerStats.getRushingLongTouchdown());
		builder.incrementReceptions(playerStats.getReceptions());
		builder.incrementReceivingYards(playerStats.getReceivingYards());
		builder.incrementReceivingTouchdowns(playerStats.getReceivingTouchdowns());
		builder.incrementReceivingLong(playerStats.getReceivingLong());
		builder.incrementReceivingLongTouchdown(playerStats.getInterceptions());
		builder.incrementFumbles(playerStats.getFumbles());
		builder.incrementFumblesLost(playerStats.getFumblesLost());
		builder.incrementFumblesRecovered(playerStats.getFumblesRecovered());
		builder.incrementFumbleYards(playerStats.getFumbleYards());
	}
}
