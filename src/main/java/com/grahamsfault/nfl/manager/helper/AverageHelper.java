package com.grahamsfault.nfl.manager.helper;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.model.PlayerStats;

import java.util.Map;

/**
 * Helper class to figure out the averages
 */
public abstract class AverageHelper {

	/**
	 * Find the statistical average of all positions
	 *
	 * @param year The year we want the average for
	 * @return The map of position to average statistics
	 */
	public abstract Map<Position, AverageStats> getAveragePerPosition(Integer year);

	protected void incrementAverageStats(AverageStats.Builder builder, PlayerStats playerStats) {
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
