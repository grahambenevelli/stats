package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.Player;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface PlayerDAO {

	Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	) throws SQLException;

	boolean isEmpty() throws SQLException;

	void updatePlayer(Player player) throws SQLException;

	Optional<Player> getById(String id) throws SQLException;
}
