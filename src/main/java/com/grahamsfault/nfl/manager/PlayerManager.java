package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.model.Player;

import java.util.Set;

public class PlayerManager extends BaseManager {

	private final PlayerDAO playerDAO;

	public PlayerManager(PlayerDAO playerDAO) {
		this.playerDAO = playerDAO;
	}

	public Set<Player> searchForPlayer(
			final String firstName,
			final String lastName
	) {
		return playerDAO.searchForPlayer(firstName, lastName);
	}

}
