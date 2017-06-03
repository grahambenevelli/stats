package com.grahamsfault.nfl.manager;

import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.file.PlayerFileReader;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PlayerManager {

	private final PlayerFileReader playerFileReader;
	private final PlayerDAO playerDAO;

	public PlayerManager(PlayerFileReader playerFileReader, PlayerDAO playerDAO) {
		this.playerFileReader = playerFileReader;
		this.playerDAO = playerDAO;
	}

	/**
	 * Search for a given player by first name or last name or both
	 */
	public Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	) {
		try {
			if (!playerDAO.isEmpty()) {
				List<Player> players = playerFileReader.allPlayers();
				for (Player player : players) {
					playerDAO.updatePlayer(player);
				}
			}

			return playerDAO.searchForPlayer(firstName, lastName);
		} catch (SQLException e) {
			throw Throwables.propagate(e);
		}
	}

}
