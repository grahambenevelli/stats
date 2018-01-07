package com.grahamsfault.stats.server.manager;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.dao.PredictionDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * The manager for handling predictions
 */
public class PredictionManager {

	private final PredictionDAO predictionDAO;

	public PredictionManager(PredictionDAO predictionDAO) {
		this.predictionDAO = predictionDAO;
	}

	/**
	 * Record the given results of each position
	 *
	 * @param name The name of the algorithm
	 * @param results The results of a run of that algorithm
	 */
	public void recordResults(final String name, final PredictionResults results) {
		List<Position> list = Arrays.asList(Position.values());
		list.forEach(position -> recordResults(name, position, results.getStats(position)));
	}

	/**
	 * Record the given results of each position
	 *
	 * @param name The name of the algorithm
	 * @param position The position the stats are for
	 * @param stats The accuracy stats
	 */
	private void recordResults(String name, Position position, AccuracyStats stats) {
		try {
			predictionDAO.recordTestRunAccuracy(name, position, stats);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
