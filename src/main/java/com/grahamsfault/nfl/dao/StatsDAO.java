package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.model.GameStats;
import com.grahamsfault.nfl.model.PlayerStats;

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
	 * Get the game stats for a given game id
	 *
	 * @param eid The game id
	 * @return The optional stats for the game if they exist
	 */
	Optional<GameStats> gameStats(String eid);
}
