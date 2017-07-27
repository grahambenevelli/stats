package com.grahamsfault.nfl.dao.mysql;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.dao.StatsDAO;
import com.grahamsfault.nfl.model.GameStats;
import com.grahamsfault.nfl.model.PlayerStats;
import org.apache.commons.lang3.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class MySQLStatsDAO implements StatsDAO {

	private final DataSource dataSource;

	public MySQLStatsDAO(DataSource statsDataSource) {
		this.dataSource = statsDataSource;
	}

	@Override
	public void updateGameStats(Game game, PlayerStats stats) throws SQLException {
		String sql = "INSERT INTO `game_stats` " +
				"(`player_id`, `game_id`, `passing_attempts`, `passing_completions`, `passing_yards`, `passing_touchdowns`, `interceptions`, `rushing_attempts`, `rushing_yards`, `rushing_touchdowns`, `rushing_long`, `rushing_long_touchdown`, `receptions`, `receiving_yards`, `receiving_touchdowns`, `receiving_long`, `receiving_long_touchdown`, `fumbles`, `fumbles_lost`, `fumbles_recovered`, `fumble_yards`)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE " +
				"`player_id`=?, " +
				"`game_id`=?, " +
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
			statement.setString(++i, stats.getId());
			statement.setString(++i, game.getEid());
			statement.setInt(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setInt(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setInt(++i, zeroOrNull(stats.getPassingYards()));
			statement.setInt(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getInterceptions()));
			statement.setInt(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setInt(++i, zeroOrNull(stats.getRushingYards()));
			statement.setInt(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getRushingLong()));
			statement.setInt(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setInt(++i, zeroOrNull(stats.getReceptions()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setInt(++i, zeroOrNull(stats.getFumbles()));
			statement.setInt(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setInt(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setInt(++i, zeroOrNull(stats.getFumbleYards()));

			statement.setString(++i, stats.getId());
			statement.setString(++i, game.getEid());
			statement.setInt(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setInt(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setInt(++i, zeroOrNull(stats.getPassingYards()));
			statement.setInt(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getInterceptions()));
			statement.setInt(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setInt(++i, zeroOrNull(stats.getRushingYards()));
			statement.setInt(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getRushingLong()));
			statement.setInt(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setInt(++i, zeroOrNull(stats.getReceptions()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setInt(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setInt(++i, zeroOrNull(stats.getFumbles()));
			statement.setInt(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setInt(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setInt(++i, zeroOrNull(stats.getFumbleYards()));

			statement.executeUpdate();
		}
	}

	@Override
	public Optional<GameStats> gameStats(String eid) {
		throw new NotImplementedException("gameStats not implemented");
	}

	private int zeroOrNull(Integer value) {
		if (value == null) {
			return 0;
		}
		return value;
	}
}
