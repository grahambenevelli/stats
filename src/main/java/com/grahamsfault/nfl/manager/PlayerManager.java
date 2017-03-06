package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.api.model.Player;

import java.util.Optional;
import java.util.Set;

public class PlayerManager {

	private final PlayerDAO playerDAO;

	public PlayerManager(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

	/**
	 * Search for a given player by first name or last name or both
	 */
	public Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	) {
		return playerDAO.searchForPlayer(firstName, lastName);
	}

}
