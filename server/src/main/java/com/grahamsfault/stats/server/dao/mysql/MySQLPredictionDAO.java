package com.grahamsfault.stats.server.dao.mysql;

import com.grahamsfault.stats.server.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.dao.PredictionDAO;
import com.grahamsfault.stats.server.dao.mysql.helper.MySQLUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.grahamsfault.stats.server.dao.mysql.helper.MySQLUtil.zeroOrNull;

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
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingAttempts()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingCompletions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getInterceptions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingAttempts()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingLong()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceptions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingLong()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumbles()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumblesLost()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumblesRecovered()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumbleYards()));

			statement.setString(++i, name);
			statement.setString(++i, position.abbreviation);
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingAttempts()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingCompletions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getPassingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getInterceptions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingAttempts()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingLong()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceptions()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingYards()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingLong()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumbles()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumblesLost()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumblesRecovered()));
			statement.setDouble(++i, MySQLUtil.zeroOrNull(stats.getFumbleYards()));

			statement.executeUpdate();
		}

	}
}
