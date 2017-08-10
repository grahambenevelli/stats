package com.grahamsfault.nfl.manager.helper;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AverageStats;

import java.util.Map;

/**
 * Helper class to figure out the averages
 */
public interface AverageHelper {

	/**
	 * Find the statistical average of all positions
	 *
	 * @param year The year we want the average for
	 * @return The map of position to average statistics
	 */
	Map<Position, AverageStats> getAveragePerPosition(Integer year);
}
