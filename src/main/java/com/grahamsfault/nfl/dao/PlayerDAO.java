package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.Player;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface PlayerDAO {

	/**
	 * Search for a given player given the first and last name, optionally
	 *
	 * @param firstName The optional first name
	 * @param lastName The optional last nae
	 * @return The set of players that match the search
	 * @throws SQLException
	 */
	Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	) throws SQLException;

	/**
	 * See if the player table is empty
	 *
	 * @return true if no data
	 * @throws SQLException
	 */
	boolean isEmpty() throws SQLException;

	/**
	 * Update the given player
	 *
	 * @param player The player object
	 * @throws SQLException
	 */
	void updatePlayer(Player player) throws SQLException;

	/**
	 * Get the player by id
	 *
	 * @param id The player id
	 * @return An optional player, if the player exists
	 * @throws SQLException
	 */
	Optional<Player> getById(String id) throws SQLException;

	/**
	 * Get the players that played in a given year
	 *
	 * @param year The year we are looking up
	 * @return A set of players that have stats for the year
	 * @throws SQLException
	 */
	Set<Player> getPlayersPerYear(int year) throws SQLException;
}
