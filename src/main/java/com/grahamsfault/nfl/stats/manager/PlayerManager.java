package com.grahamsfault.nfl.stats.manager;

import com.google.common.base.Throwables;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.dao.PlayerDAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

/**
 * Manager class for managing player information
 */
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
		try {
			return playerDAO.searchForPlayer(firstName, lastName);
		} catch (SQLException e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * Update the given player record
	 *
	 * @param player The player to update
	 */
	public void updatePlayer(Player player) {
		try {
			playerDAO.updatePlayer(player);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get a player by id
	 *
	 * @param playerId The id of the player
	 * @return An optional player
	 */
	public Optional<Player> getById(Long playerId) {
		try {
			return playerDAO.getById(playerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
