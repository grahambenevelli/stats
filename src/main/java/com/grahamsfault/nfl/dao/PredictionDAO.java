package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AccuracyStats;

import java.sql.SQLException;

/**
 * The prediction DAO
 */
public interface PredictionDAO {

	/**
	 * Record the results of a stats prediction algorithm
	 *
	 * @param name The name of the algorithm
	 * @param position The position predicted
	 * @param stats The accuracy of the prediction
	 * @throws SQLException
	 */
	void recordTestRunAccuracy(String name, Position position, AccuracyStats stats) throws SQLException;
}
