package com.grahamsfault.stats.server.dao.mysql;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.stats.server.dao.ImportDAO;
import com.grahamsfault.stats.server.model.GameImportLog;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MySQLImportDAO implements ImportDAO {

	private final DataSource dataSource;

	public MySQLImportDAO(DataSource statsDataSource) {
		this.dataSource = statsDataSource;
	}

	@Override
	public boolean hasImported(String gameId) throws SQLException {
		String sql = "SELECT * FROM game_stats_import_log WHERE game_id = ?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, gameId);

			try (ResultSet result = statement.executeQuery()) {
				return result.next();
			}
		}
	}

	@Override
	public void markAsImported(String gameId) throws SQLException {
		String sql = "INSERT INTO `game_stats_import_log` " +
				"(`game_id`)" +
				"VALUES (?) " +
				"ON DUPLICATE KEY UPDATE " +
				"`game_id`=?, " +
				"`updated`=CURRENT_TIMESTAMP();";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setString(++i, gameId);
			statement.setString(++i, gameId);

			statement.executeUpdate();
		}
	}

	@Override
	public List<Integer> getYears() throws SQLException {
		String sql = "SELECT DISTINCT year FROM games";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				return consumeYearResultSet(result);
			}
		}
	}

	/**
	 * Consume the result set for the years
	 *
	 * @param result
	 * @return
	 * @throws SQLException
	 * TODO test
	 */
	private List<Integer> consumeYearResultSet(ResultSet result) throws SQLException {
		ImmutableList.Builder<Integer> builder = ImmutableList.builder();
		while (result.next()) {
			Integer year = result.getInt("year");
			builder.add(year);
		}
		return builder.build();
	}

	@Override
	public void compileYearlyStats(int year) throws SQLException {
		String sql = "insert into yearly_stats\n" +
				"select gs.player_id,\n" +
				"\tyear,\n" +
				"\tSUM(passing_attempts) as passing_attempts,\n" +
				"\tSUM(passing_completions) as passing_completions,\n" +
				"    SUM(passing_yards) as passing_yards,\n" +
				"    SUM(passing_touchdowns) as passing_touchdowns,\n" +
				"    SUM(interceptions) as interceptions,\n" +
				"    SUM(rushing_attempts) as rushing_attempts,\n" +
				"    SUM(rushing_yards) as rushing_yards,\n" +
				"    SUM(rushing_touchdowns) as rushing_touchdowns,\n" +
				"    MAX(rushing_long) as rushing_long,\n" +
				"    MAX(rushing_long_touchdown) as rushing_long_touchdown,\n" +
				"    SUM(receptions) as receptions,\n" +
				"    SUM(receiving_yards) as receiving_yards,\n" +
				"    SUM(receiving_touchdowns) as receiving_touchdowns,\n" +
				"    MAX(receiving_long) as receiving_long,\n" +
				"    MAX(receiving_long_touchdown) as receiving_long_touchdown,\n" +
				"    SUM(fumbles) as fumbles,\n" +
				"    SUM(fumbles_lost) as fumbles_lost,\n" +
				"    SUM(fumbles_recovered) as fumbles_recovered,\n" +
				"    SUM(fumble_yards) as fumble_yards\n" +
				"from game_stats gs\n" +
				"\tjoin games g on gs.game_id = g.eid\n" +
				"where year = ?\n" +
				"\tand season_type = 'REG'\n" +
				"group by player_id;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setInt(i++, year);
			statement.executeUpdate();
		}
	}

	@Override
	public void recordPlayerIdForImport(int year, String s) throws SQLException {
		String sql = "INSERT INTO `player_id_import_log` " +
				"(`gsis_id`, `year`, `basic_info_imported`)" +
				"VALUES (?, ?, 0) " +
				"ON DUPLICATE KEY UPDATE " +
				"`gsis_id`=?, " +
				"`year`=?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setString(i++, s);
			statement.setInt(i++, year);
			statement.setString(i++, s);
			statement.setInt(i++, year);

			statement.executeUpdate();
		}
	}

	@Override
	public void markPlayerIdsAsImported(String eid) throws SQLException {
		String sql = "INSERT INTO `game_import_log` " +
				"(`eid`, `player_id_imported`)" +
				"VALUES (?, 1) " +
				"ON DUPLICATE KEY UPDATE " +
				"`player_id_imported`=1;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setString(i++, eid);
			statement.executeUpdate();
		}
	}

	@Override
	public Optional<GameImportLog> getImportLog(String eid) throws SQLException {
		String sql = "SELECT * FROM game_import_log WHERE eid = ?";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setString(i, eid);
			try (ResultSet result = statement.executeQuery()) {
				return consumeGameImportLogFromResultSet(result).stream().findFirst();
			}
		}
	}

	@Override
	public List<String> getPlayerIdsForImport() throws SQLException {
		String sql = "SELECT * FROM player_id_import_log WHERE basic_info_imported = 0";
		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			try (ResultSet result = statement.executeQuery()) {
				ImmutableList.Builder<String> builder = ImmutableList.builder();
				while (result.next()) {
					builder.add(result.getString("gsis_id"));
				}
				return builder.build();
			}
		}
	}

	@Override
	public void markPlayerBasicInfoImported(String playerId) throws SQLException {
		String sql = "UPDATE player_id_import_log SET basic_info_imported = 1 WHERE gsis_id = ?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			statement.setString(i++, playerId);
			statement.executeUpdate();
		}
	}

	@Override
	public void truncateYearlyStats() throws SQLException {
		String sql = "TRUNCATE TABLE yearly_stats";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.execute();
		}
	}

	/**
	 * Consume the result set for an game import log
	 *
	 * @param result The result set from the DB
	 * @return The matching list of GameImportLogs
	 * @throws SQLException
	 * TODO test
	 */
	private List<GameImportLog> consumeGameImportLogFromResultSet(ResultSet result) throws SQLException {
		ImmutableList.Builder<GameImportLog> builder = ImmutableList.builder();
		while (result.next()) {
			GameImportLog log = GameImportLog.builder()
					.eid(result.getString("eid"))
					.playerIdImported(result.getBoolean("player_id_imported"))
					.build();

			builder.add(log);
		}
		return builder.build();
	}
}
