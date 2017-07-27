package com.grahamsfault.nfl.dao.mysql;

import com.grahamsfault.nfl.dao.ImportDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
