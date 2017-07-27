package com.grahamsfault.nfl.dao.mysql;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.dao.GameDAO;
import org.apache.commons.lang3.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MySQLGameDAO implements GameDAO {

	private final DataSource dataSource;

	public MySQLGameDAO(DataSource stats) {
		this.dataSource = stats;
	}

	@Override
	public List<Game> searchGames(int year, GameType gameType, Optional<Integer> week, Optional<Team> home, Optional<Team> away) {
		throw new NotImplementedException("searchGames is not yet implemented");
	}

	@Override
	public void updateGame(Game game) throws SQLException {
		String sql = "INSERT INTO `games` " +
				"(`eid`, `home`, `away`, `day`, `gamekey`, `meridiem`, `month`, `season_type`, `time`, `wday`, `week`, `year`)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE " +
				"`eid`=?, " +
				"`home`=?, " +
				"`away`=?, " +
				"`day`=?, " +
				"`gamekey`=?, " +
				"`meridiem`=?, " +
				"`month`=?, " +
				"`season_type`=?, " +
				"`time`=?, " +
				"`wday`=?, " +
				"`week`=?, " +
				"`year`=?;";


		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setString(++i, game.getEid());
			statement.setString(++i, game.getHome().abbreviation);
			statement.setString(++i, game.getAway().abbreviation);
			statement.setInt(++i, game.getDay());
			statement.setString(++i, game.getGamekey());
			statement.setString(++i, game.getMeridiem());
			statement.setInt(++i, game.getMonth());
			statement.setString(++i, game.getSeasonType().type);
			statement.setString(++i, game.getTime());
			statement.setString(++i, game.getWday());
			statement.setInt(++i, game.getWeek());
			statement.setInt(++i, game.getYear());

			statement.setString(++i, game.getEid());
			statement.setString(++i, game.getHome().abbreviation);
			statement.setString(++i, game.getAway().abbreviation);
			statement.setInt(++i, game.getDay());
			statement.setString(++i, game.getGamekey());
			statement.setString(++i, game.getMeridiem());
			statement.setInt(++i, game.getMonth());
			statement.setString(++i, game.getSeasonType().type);
			statement.setString(++i, game.getTime());
			statement.setString(++i, game.getWday());
			statement.setInt(++i, game.getWeek());
			statement.setInt(++i, game.getYear());

			statement.executeUpdate();
		}
	}

	/**
	 * Delete the given record from the database
	 *
	 * @param eid The id of the game to delete
	 * @throws SQLException
	 */
	@VisibleForTesting
	protected void deleteGame(String eid) throws SQLException {
		String sql = "DELETE FROM `games` " +
				"WHERE eid=?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, eid);
			statement.execute();
		}
	}

	@Override
	public Optional<Game> getById(String id) throws SQLException {
		String sql = "SELECT * FROM games WHERE eid = ?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, id);

			try (ResultSet result = statement.executeQuery()) {
				Set<Game> games = consumeGameResult(result);
				return Optional.ofNullable(Iterables.getFirst(games, null));
			}
		}
	}

	@Override
	public Set<Game> allGames() throws SQLException {
		String sql = "SELECT * FROM games;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				return consumeGameResult(result);
			}
		}
	}

	/**
	 * Consume the game result from a result set
	 *
	 * @param result The result from the database
	 * @return The set of games from the result set
	 * @throws SQLException
	 */
	@VisibleForTesting
	protected Set<Game> consumeGameResult(ResultSet result) throws SQLException {
		ImmutableSet.Builder<Game> ret = ImmutableSet.builder();
		while (result.next()) {
			String away = result.getString("away");
			String home = result.getString("home");
			String seasonType = result.getString("season_type");

			ret.add(new Game(
					Team.forValue(away),
					Team.forValue(home),
					result.getInt("day"),
					result.getString("eid"),
					result.getString("gamekey"),
					result.getString("meridiem"),
					result.getInt("month"),
					GameType.forValue(seasonType),
					result.getString("time"),
					result.getString("wday"),
					result.getInt("week"),
					result.getInt("year")
			));
		}
		return ret.build();
	}
}
