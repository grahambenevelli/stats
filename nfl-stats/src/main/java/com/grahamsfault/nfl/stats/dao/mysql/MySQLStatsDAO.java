package com.grahamsfault.nfl.stats.dao.mysql;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.dao.StatsDAO;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
			statement.setLong(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setLong(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setLong(++i, zeroOrNull(stats.getPassingYards()));
			statement.setLong(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getInterceptions()));
			statement.setLong(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setLong(++i, zeroOrNull(stats.getRushingYards()));
			statement.setLong(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getRushingLong()));
			statement.setLong(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setLong(++i, zeroOrNull(stats.getReceptions()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setLong(++i, zeroOrNull(stats.getFumbles()));
			statement.setLong(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setLong(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setLong(++i, zeroOrNull(stats.getFumbleYards()));

			statement.setString(++i, stats.getId());
			statement.setString(++i, game.getEid());
			statement.setLong(++i, zeroOrNull(stats.getPassingAttempts()));
			statement.setLong(++i, zeroOrNull(stats.getPassingCompletions()));
			statement.setLong(++i, zeroOrNull(stats.getPassingYards()));
			statement.setLong(++i, zeroOrNull(stats.getPassingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getInterceptions()));
			statement.setLong(++i, zeroOrNull(stats.getRushingAttempts()));
			statement.setLong(++i, zeroOrNull(stats.getRushingYards()));
			statement.setLong(++i, zeroOrNull(stats.getRushingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getRushingLong()));
			statement.setLong(++i, zeroOrNull(stats.getRushingLongTouchdown()));
			statement.setLong(++i, zeroOrNull(stats.getReceptions()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingYards()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingTouchdowns()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingLong()));
			statement.setLong(++i, zeroOrNull(stats.getReceivingLongTouchdown()));
			statement.setLong(++i, zeroOrNull(stats.getFumbles()));
			statement.setLong(++i, zeroOrNull(stats.getFumblesLost()));
			statement.setLong(++i, zeroOrNull(stats.getFumblesRecovered()));
			statement.setLong(++i, zeroOrNull(stats.getFumbleYards()));

			statement.executeUpdate();
		}
	}

	@Override
	public Optional<PlayerStats> getPlayerYearlyStats(Player player, Integer year) throws SQLException {
		String sql = "select *\n" +
				"from players p\n" +
				"\tjoin yearly_stats ys on p.gsis_id = ys.player_id\n" +
				"where p.gsis_id = ?\n" +
				"\tand ys.year = ?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, player.getGsisId());
			statement.setInt(2, year);

			try (ResultSet result = statement.executeQuery()) {
				return consumePlayerStatsResults(result, player).stream().findFirst();
			}
		}
	}

	/**
	 * Consume the stats results from the given result set
	 *
	 * @param result The result set
	 * @param player The player we are building stats for
	 * @return The list of player stats
	 * @throws SQLException
	 * TODO test
	 */
	private List<PlayerStats> consumePlayerStatsResults(ResultSet result, Player player) throws SQLException {
		ImmutableList.Builder<PlayerStats> listBuilder = ImmutableList.<PlayerStats>builder();
		while (result.next()) {
			PlayerStats.Builder builder = PlayerStats.builder(player.getFullName(), player.getGsisId());

			builder.passingAttempts(zeroOrNull(result.getLong("passing_attempts")));
			builder.passingCompletions(zeroOrNull(result.getLong("passing_completions")));
			builder.passingYards(zeroOrNull(result.getLong("passing_yards")));
			builder.passingTouchdowns(zeroOrNull(result.getLong("passing_touchdowns")));
			builder.interceptions(zeroOrNull(result.getLong("interceptions")));
			builder.rushingAttempts(zeroOrNull(result.getLong("rushing_attempts")));
			builder.rushingYards(zeroOrNull(result.getLong("rushing_yards")));
			builder.rushingTouchdowns(zeroOrNull(result.getLong("rushing_touchdowns")));
			builder.rushingLong(zeroOrNull(result.getLong("rushing_long")));
			builder.rushingLongTouchdown(zeroOrNull(result.getLong("rushing_long_touchdown")));
			builder.receptions(zeroOrNull(result.getLong("receptions")));
			builder.receivingYards(zeroOrNull(result.getLong("receiving_yards")));
			builder.receivingTouchdowns(zeroOrNull(result.getLong("receiving_touchdowns")));
			builder.receivingLong(zeroOrNull(result.getLong("receiving_long")));
			builder.receivingLongTouchdown(zeroOrNull(result.getLong("receiving_long_touchdown")));
			builder.fumbles(zeroOrNull(result.getLong("fumbles")));
			builder.fumblesLost(zeroOrNull(result.getLong("fumbles_lost")));
			builder.fumblesRecovered(zeroOrNull(result.getLong("fumbles_recovered")));
			builder.fumbleYards(zeroOrNull(result.getLong("fumble_yards")));

			listBuilder.add(builder.build());
		}

		return listBuilder.build();
	}

	private long zeroOrNull(Long value) {
		if (value == null) {
			return 0;
		}
		return value;
	}
}
