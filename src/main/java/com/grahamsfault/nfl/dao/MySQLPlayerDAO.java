package com.grahamsfault.nfl.dao;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.player.Position;

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

	private Set<Player> consumePlayerResults(ResultSet result) throws SQLException {
		ImmutableSet.Builder<Player> ret = ImmutableSet.builder();
		while (result.next()) {
			String team = result.getString("team");
			ret.add(new Player(
					result.getString("birthdate"),
					result.getString("college"),
					result.getString("first_name"),
					result.getString("last_name"),
					result.getString("full_name"),
					result.getString("gsis_id"),
					result.getString("gsis_name"),
					result.getLong("profile_id"),
					result.getURL("profile_url"),
					result.getInt("height"),
					result.getInt("weight"),
					result.getInt("years_pro"),
					result.getInt("number"),
					result.getString("status"),
					Team.forValue(team),
					Position.forValue(result.getString("position"))
			));
		}
		return ret.build();
	}

	@Override
	public Optional<Player> getById(String id) throws SQLException {
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
			statement.setInt(++i, player.getHeight());
			statement.setInt(++i, Optional.ofNullable(player.getNumber()).orElse(0));
			statement.setLong(++i, player.getProfileId());
			statement.setString(++i, player.getProfileUrl().toString());
			statement.setInt(++i, player.getWeight());
			statement.setInt(++i, player.getYearsPro());
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
			statement.setInt(++i, player.getHeight());
			statement.setInt(++i, Optional.ofNullable(player.getNumber()).orElse(0));
			statement.setLong(++i, player.getProfileId());
			statement.setString(++i, player.getProfileUrl().toString());
			statement.setInt(++i, player.getWeight());
			statement.setInt(++i, player.getYearsPro());
			statement.setString(++i, player.getBirthdate());
			statement.setString(++i, player.getCollege());
			statement.setString(++i, player.getStatus());
			statement.setString(++i, player.getTeam() == null ? null : player.getTeam().abbreviation);
			statement.setString(++i, Optional.ofNullable(player.getPosition()).map(position -> position.abbreviation).orElse(null));

			statement.executeUpdate();
		}
	}

	private String getGsisNameForSaving(Player player) {
		if (player.getGsisName() != null) {
			return player.getGsisName();
		}
		return player.getFirstName().substring(0, 1) + "," + player.getLastName();
	}

	@VisibleForTesting
	protected void deletePlayer(String gsisId) throws SQLException {
		String sql = "DELETE FROM `players` " +
				"WHERE gsis_id=?";

		try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, gsisId);
			statement.execute();
		}
	}
}
