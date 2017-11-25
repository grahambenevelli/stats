package com.grahamsfault.nfl.stats.dao;

import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import java.sql.SQLException;
import java.util.Optional;

public interface StatsDAO {

	/**
	 * Update the game stats
	 *
	 * @param game The game to save data for
	 * @param stats The stats for a given player
	 * @throws SQLException
	 */
	void updateGameStats(Game game, PlayerStats stats) throws SQLException;

	/**
	 * Get the yearly stats for the given player
	 *
	 * @param player The player we want stats for
	 * @param year The year of his stats
	 * @return An optional version of the player's stats
	 * @throws SQLException
	 */
	Optional<PlayerStats> getPlayerYearlyStats(Player player, Integer year) throws SQLException;
}
