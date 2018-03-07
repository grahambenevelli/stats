package com.grahamsfault.stats.server.dao.mysql;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.impl.Tuple;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.dao.StatsDAO;
import com.grahamsfault.stats.server.dao.mysql.consumer.PlayerConsumer;
import com.grahamsfault.stats.server.dao.mysql.consumer.ReadOnlyResultSet;
import com.grahamsfault.stats.server.model.PlayerStats;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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

	@Override
	public Map<Position, StdDevStats> getStdDevByPosition() throws SQLException {
		String select = "select p.position,\n" +
				"\tstddev(passing_attempts) as passing_attempts,\n" +
				"\tstddev(passing_completions) as passing_completions,\n" +
				"\tstddev(passing_yards) as passing_yards,\n" +
				"\tstddev(passing_touchdowns) as passing_touchdowns,\n" +
				"\tstddev(interceptions) as interceptions,\n" +
				"\tstddev(rushing_attempts) as rushing_attempts,\n" +
				"\tstddev(rushing_yards) as rushing_yards,\n" +
				"\tstddev(rushing_touchdowns) as rushing_touchdowns,\n" +
				"\tstddev(rushing_long) as rushing_long,\n" +
				"\tstddev(rushing_long_touchdown) as rushing_long_touchdown,\n" +
				"\tstddev(receptions) as receptions,\n" +
				"\tstddev(receiving_yards) as receiving_yards,\n" +
				"\tstddev(receiving_touchdowns) as receiving_touchdowns,\n" +
				"\tstddev(receiving_long) as receiving_long,\n" +
				"\tstddev(receiving_long_touchdown) as receiving_long_touchdown,\n" +
				"\tstddev(fumbles) as fumbles,\n" +
				"\tstddev(fumbles_lost) as fumbles_lost,\n" +
				"\tstddev(fumbles_recovered) as fumbles_recovered,\n" +
				"\tstddev(fumble_yards) as fumble_yards\n" +
				"from yearly_stats ys\n" +
				"join players p on ys.player_id = p.gsis_id\n" +
				"where position is not null\n" +
				"group by position";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(select)) {
			try (ResultSet result = statement.executeQuery()) {
				return consumeStdDevByPosition(result);
			}
		}

	}

	@Override
	public Map<Tuple<Player, Year>, PlayerStats> getYearlyPositionStats(Position position, Integer year) throws SQLException {
		String sql = "select p.*, ys.*\n" +
				"from yearly_stats ys\n" +
				"\tjoin players p on ys.player_id = p.gsis_id\n" +
				"where year = ?\n" +
				"\tand position = ?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setInt(++i, year);
			statement.setString(++i, position.abbreviation);

			try (ResultSet result = statement.executeQuery()) {
				return consumeYearlyPositionStats(result);
			}
		}
	}

	private Map<Tuple<Player, Year>, PlayerStats> consumeYearlyPositionStats(ResultSet result) throws SQLException {
		ImmutableMap.Builder<Tuple<Player, Year>, PlayerStats> builder = ImmutableMap.builder();
		while(result.next()) {
			PlayerStats playerStats = consumePlayerStats(result);
			Player player = PlayerConsumer.consumer()
					.consume(ReadOnlyResultSet.of(result));
			Year year = Year.fromIntValue(result.getInt("year"));

			builder.put(Tuple.of(player, year), playerStats);
		}

		return builder.build();
	}

	private Map<Position, StdDevStats> consumeStdDevByPosition(ResultSet result) throws SQLException {
		ImmutableMap.Builder<Position, StdDevStats> mapBuilder = ImmutableMap.builder();

		while(result.next()) {
			Position position = Position.forValue(result.getString("position"));
			if (position == null) {
				throw new IllegalArgumentException("Position cannot be null");
			}

			StdDevStats stdDevStats = StdDevStats.builder()
					.passingAttempts(result.getDouble("passing_attempts"))
					.passingAttempts(result.getDouble("passing_completions"))
					.passingAttempts(result.getDouble("passing_yards"))
					.passingAttempts(result.getDouble("passing_touchdowns"))
					.passingAttempts(result.getDouble("interceptions"))
					.passingAttempts(result.getDouble("rushing_attempts"))
					.passingAttempts(result.getDouble("rushing_yards"))
					.passingAttempts(result.getDouble("rushing_touchdowns"))
					.passingAttempts(result.getDouble("rushing_long"))
					.passingAttempts(result.getDouble("rushing_long_touchdown"))
					.passingAttempts(result.getDouble("receptions"))
					.passingAttempts(result.getDouble("receiving_yards"))
					.passingAttempts(result.getDouble("receiving_touchdowns"))
					.passingAttempts(result.getDouble("receiving_long"))
					.passingAttempts(result.getDouble("receiving_long_touchdown"))
					.passingAttempts(result.getDouble("fumbles"))
					.passingAttempts(result.getDouble("fumbles_lost"))
					.passingAttempts(result.getDouble("fumbles_recovered"))
					.passingAttempts(result.getDouble("fumble_yards"))
					.build();

			mapBuilder.put(position, stdDevStats);
		}

		return mapBuilder.build();
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
			PlayerStats playerStats = consumePlayerStats(result);
			listBuilder.add(playerStats);
		}

		return listBuilder.build();
	}

	private PlayerStats consumePlayerStats(ResultSet result) throws SQLException {
		return PlayerStats.builder(result.getString("full_name"),result.getString("gsis_id"))
				.passingAttempts(zeroOrNull(result.getLong("passing_attempts")))
				.passingCompletions(zeroOrNull(result.getLong("passing_completions")))
				.passingYards(zeroOrNull(result.getLong("passing_yards")))
				.passingTouchdowns(zeroOrNull(result.getLong("passing_touchdowns")))
				.interceptions(zeroOrNull(result.getLong("interceptions")))
				.rushingAttempts(zeroOrNull(result.getLong("rushing_attempts")))
				.rushingYards(zeroOrNull(result.getLong("rushing_yards")))
				.rushingTouchdowns(zeroOrNull(result.getLong("rushing_touchdowns")))
				.rushingLong(zeroOrNull(result.getLong("rushing_long")))
				.rushingLongTouchdown(zeroOrNull(result.getLong("rushing_long_touchdown")))
				.receptions(zeroOrNull(result.getLong("receptions")))
				.receivingYards(zeroOrNull(result.getLong("receiving_yards")))
				.receivingTouchdowns(zeroOrNull(result.getLong("receiving_touchdowns")))
				.receivingLong(zeroOrNull(result.getLong("receiving_long")))
				.receivingLongTouchdown(zeroOrNull(result.getLong("receiving_long_touchdown")))
				.fumbles(zeroOrNull(result.getLong("fumbles")))
				.fumblesLost(zeroOrNull(result.getLong("fumbles_lost")))
				.fumblesRecovered(zeroOrNull(result.getLong("fumbles_recovered")))
				.fumbleYards(zeroOrNull(result.getLong("fumble_yards")))
				.build();
	}

	private long zeroOrNull(Long value) {
		if (value == null) {
			return 0;
		}
		return value;
	}

}
