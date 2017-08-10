package com.grahamsfault.nfl.dao.mysql;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AccuracyStats;
import com.grahamsfault.nfl.dao.PredictionDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.grahamsfault.nfl.dao.mysql.helper.MySQLUtil.zeroOrNull;

public class MySQLPredictionDAO implements PredictionDAO {
	private final DataSource dataSource;

	public MySQLPredictionDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void recordTestRunAccuracy(String name, Position position, AccuracyStats stats) throws SQLException {
		String sql = "INSERT INTO `prediction_accuracy` " +
				"(`name`, `position`, `passing_attempts`, `passing_completions`, `passing_yards`, `passing_touchdowns`, `interceptions`, `rushing_attempts`, `rushing_yards`, `rushing_touchdowns`, `rushing_long`, `rushing_long_touchdown`, `receptions`, `receiving_yards`, `receiving_touchdowns`, `receiving_long`, `receiving_long_touchdown`, `fumbles`, `fumbles_lost`, `fumbles_recovered`, `fumble_yards`)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE " +
				"`name`=?, " +
				"`position`=?, " +
				"`passing_attempts`=?, " +
				"`passing_completions`=?, " +
				"`passing_yards`=?, " +
				"`passing_touchdowns`=?, " +
				"`interceptions`=?, " +
				"`rushing_attempts`=?, " +
				"`rushing_yards`=?, " +
				"`rushing_touchdowns`=?, " +
				"`rushing_long`=?, " +
				"`rushing_long_touchdown`=?, " +
				"`receptions`=?, " +
				"`receiving_yards`=?, " +
				"`receiving_touchdowns`=?, " +
				"`receiving_long`=?, " +
				"`receiving_long_touchdown`=?, " +
				"`fumbles`=?, " +
				"`fumbles_lost`=?, " +
				"`fumbles_recovered`=?, " +
				"`fumble_yards`=?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setString(++i, name);
			statement.setString(++i, position.abbreviation);
			statement.setDouble(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getInterceptions()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingLong()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setDouble(++i, zeroOrNull(stats.getReceptions()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setDouble(++i, zeroOrNull(stats.getFumbles()));
			statement.setDouble(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setDouble(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setDouble(++i, zeroOrNull(stats.getFumbleYards()));

			statement.setString(++i, name);
			statement.setString(++i, position.abbreviation);
			statement.setDouble(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getInterceptions()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingLong()));
			statement.setDouble(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setDouble(++i, zeroOrNull(stats.getReceptions()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setDouble(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setDouble(++i, zeroOrNull(stats.getFumbles()));
			statement.setDouble(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setDouble(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setDouble(++i, zeroOrNull(stats.getFumbleYards()));

			statement.executeUpdate();
		}

	}
}
