package com.grahamsfault.stats.server.dao.mysql;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.stats.server.dao.PlayerDAO;
import com.grahamsfault.stats.server.dao.mysql.consumer.PlayerConsumer;
import com.grahamsfault.stats.server.dao.mysql.consumer.ReadOnlyResultSet;
import com.grahamsfault.stats.server.manager.helper.QualifyingNumbersHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class MySQLPlayerDAO implements PlayerDAO {

	private final DataSource dataSource;

	public MySQLPlayerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Set<Player> searchForPlayer(Optional<String> firstName, Optional<String> lastName) throws SQLException {
		String sql = "SELECT * FROM players WHERE\n";

		sql += Joiner.on(" AND ").join(
				firstName.map(s -> "first_name like ?").orElse("TRUE"),
				lastName.map(s -> "last_name like ?").orElse("TRUE")
		);

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 1;
			if (firstName.isPresent()) {
				statement.setString(i++, "%" + firstName.get() + "%");
			}

			if (lastName.isPresent()) {
				statement.setString(i++, "%" + lastName.get() + "%");
			}

			try (ResultSet result = statement.executeQuery()) {
				return consumePlayerResults(result);
			}
		}
	}

	/**
	 * Convert the result set into a set of players
	 *
	 * @param result The result set from the database
	 * @return The set of players in the result set
	 * @throws SQLException TODO test
	 */
	@VisibleForTesting
	protected Set<Player> consumePlayerResults(ResultSet result) throws SQLException {
		ImmutableSet.Builder<Player> ret = ImmutableSet.builder();
		while (result.next()) {
			ret.add(
					PlayerConsumer.consumer()
							.consume(ReadOnlyResultSet.of(result))
			);
		}
		return ret.build();
	}

	@Override
	public Optional<Player> getByGsisId(String id) throws SQLException {
		String sql = "SELECT * FROM players WHERE gsis_id = ?;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, id);

			try (ResultSet result = statement.executeQuery()) {
				Set<Player> players = consumePlayerResults(result);
				return Optional.ofNullable(Iterables.getFirst(players, null));
			}
		}
	}

	@Override
	public boolean isEmpty() throws SQLException {
		String sql = "SELECT count(*) as count FROM players;";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql);
			 ResultSet result = statement.executeQuery()
		) {
			result.next();
			return result.getInt("count") == 0;
		}
	}

	@Override
	public void updatePlayer(Player player) throws SQLException {
		String sql = "INSERT INTO `players` " +
				"(`gsis_id`, `first_name`, `last_name`, `full_name`, `gsis_name`, `height`, `number`, `profile_id`, `profile_url`, `weight`, `years_pro`, `birthdate`, `college`, `status`, `team`, `position`)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE " +
				"`gsis_id`=?, " +
				"`first_name`=?, " +
				"`last_name`=?, " +
				"`full_name`=?, " +
				"`gsis_name`=?, " +
				"`height`=?, " +
				"`number`=?, " +
				"`profile_id`=?, " +
				"`profile_url`=?, " +
				"`weight`=?, " +
				"`years_pro`=?," +
				"`birthdate`=?," +
				"`college`=?," +
				"`status`=?," +
				"`team`=?," +
				"`position`=?;";


		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setString(++i, player.getGsisId());
			statement.setString(++i, player.getFirstName());
			statement.setString(++i, player.getLastName());
			statement.setString(++i, player.getFullName());
			statement.setString(++i, getGsisNameForSaving(player));
			statement.setInt(++i, Optional.ofNullable(player.getHeight()).orElse(0));
			statement.setInt(++i, Optional.ofNullable(player.getNumber()).orElse(0));
			statement.setLong(++i, Optional.ofNullable(player.getProfileId()).orElse(0L));
			statement.setString(++i, player.getProfileUrl().toString());
			statement.setInt(++i, Optional.ofNullable(player.getWeight()).orElse(0));
			statement.setInt(++i, Optional.ofNullable(player.getExperience()).orElse(0));
			statement.setString(++i, player.getBirthdate());
			statement.setString(++i, player.getCollege());
			statement.setString(++i, player.getStatus());
			statement.setString(++i, player.getTeam() == null ? null : player.getTeam().abbreviation);
			statement.setString(++i, Optional.ofNullable(player.getPosition()).map(position -> position.abbreviation).orElse(null));

			statement.setString(++i, player.getGsisId());
			statement.setString(++i, player.getFirstName());
			statement.setString(++i, player.getLastName());
			statement.setString(++i, player.getFullName());
			statement.setString(++i, getGsisNameForSaving(player));
			statement.setInt(++i, Optional.ofNullable(player.getHeight()).orElse(0));
			statement.setInt(++i, Optional.ofNullable(player.getNumber()).orElse(0));
			statement.setLong(++i, Optional.ofNullable(player.getProfileId()).orElse(0L));
			statement.setString(++i, player.getProfileUrl().toString());
			statement.setInt(++i, Optional.ofNullable(player.getWeight()).orElse(0));
			statement.setInt(++i, Optional.ofNullable(player.getExperience()).orElse(0));
			statement.setString(++i, player.getBirthdate());
			statement.setString(++i, player.getCollege());
			statement.setString(++i, player.getStatus());
			statement.setString(++i, player.getTeam() == null ? null : player.getTeam().abbreviation);
			statement.setString(++i, Optional.ofNullable(player.getPosition()).map(position -> position.abbreviation).orElse(null));

			statement.executeUpdate();
		}
	}

	/**
	 * Get the comma separated name of the player
	 */
	private String getGsisNameForSaving(Player player) {
		if (player.getGsisName() != null) {
			return player.getGsisName();
		}
		return player.getFirstName().substring(0, 1) + "," + player.getLastName();
	}

	@Override
	public Set<Player> getPlayersPerYear(int year) throws SQLException {
		String sql = "select p.*\n" +
				"from players p\n" +
				"\tjoin game_stats gs on p.gsis_id = gs.player_id\n" +
				"\tjoin games g on gs.game_id = g.eid\n" +
				"where year = ?\n" +
				"\tAND p.position is not null\n" +
				"group by p.gsis_id;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, year);

			try (ResultSet result = statement.executeQuery()) {
				return consumePlayerResults(result);
			}
		}
	}

	/**
	 * Delete a given player by id, only used in integration tests
	 *
	 * @param gsisId The id of the player
	 * @throws SQLException
	 */
	@VisibleForTesting
	protected void deletePlayer(String gsisId) throws SQLException {
		String sql = "DELETE FROM `players` " +
				"WHERE gsis_id=?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, gsisId);
			statement.execute();
		}
	}

	@Override
	public Optional<Player> getById(Long playerId) throws SQLException {
		String sql = "select p.*\n" +
				"from players p\n" +
				"where id = ?\n";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, playerId);

			try (ResultSet result = statement.executeQuery()) {
				return consumePlayerResults(result).stream().findFirst();
			}
		}
	}

	@Override
	public Set<Player> getQualifyingPlayersForYear(int year) throws SQLException {
		String sql = "select p.*\n" +
				"from players p\n" +
				"join yearly_stats ys on p.gsis_id = ys.player_id\n" +
				"where year = ?\n" +
				"AND p.position is not null\n" +
				"AND (" +
				"(p.position = 'QB' AND ys.passing_attempts > ?) OR " +
				"(p.position = 'RB' AND (ys.rushing_attempts > ? OR ys.receptions > ?)) OR " +
				"(p.position = 'WR' AND ys.receptions > ?) OR " +
				"(p.position = 'TE' AND ys.receptions > ?)" +
				")" +
				"group by p.gsis_id;";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			int i = 0;
			statement.setInt(++i, year);
			statement.setInt(++i, QualifyingNumbersHelper.DEFAULT_QB_QUALIFYING_PASSING_ATTEMPTS);
			statement.setInt(++i, QualifyingNumbersHelper.DEFAULT_RB_QUALIFYING_RUSHING_ATTEMPTS);
			statement.setInt(++i, QualifyingNumbersHelper.DEFAULT_RB_QUALIFYING_RECEPTIONS);
			statement.setInt(++i, QualifyingNumbersHelper.DEFAULT_WR_QUALIFYING_RECEPTIONS);
			statement.setInt(++i, QualifyingNumbersHelper.DEFAULT_TE_QUALIFYING_RECEPTIONS);

			try (ResultSet result = statement.executeQuery()) {
				return consumePlayerResults(result);
			}
		}
	}
}
