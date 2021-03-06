package com.grahamsfault.stats.server.manager;

import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.stats.server.dao.PlayerDAO;

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

	/**
	 * Get the set of players that we have stats for in a given year
	 *
	 * @param year The year to get players for
	 * @return The set of players that have stats
	 */
	public Set<Player> getPlayersPerYear(int year) {
		try {
			return playerDAO.getPlayersPerYear(year);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Set<Player> getQualifyingPlayersForYear(int year) {
		try {
			return playerDAO.getQualifyingPlayersForYear(year);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
