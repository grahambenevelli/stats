package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.dao.StatsDAO;
import com.grahamsfault.nfl.model.GameStats;
import com.grahamsfault.nfl.model.PlayerStats;

import java.sql.SQLException;
import java.util.Optional;

public class StatsManager {

	private final StatsDAO statsDAO;

	public StatsManager(StatsDAO statsDAO) {
		this.statsDAO = statsDAO;
	}

	/**
	 * Get the games stats for the given EID
	 *
	 * @param eid The id of the game
	 * @return An optional version of the game's stats
	 */
	public Optional<GameStats> gameStats(String eid) {
		return statsDAO.gameStats(eid);
	}

	/**
	 * Update the game stats stored in the database
	 *
	 * @param game The game the stats are for
	 * @param stats The stats to save
	 */
	public void updateGameStats(Game game, PlayerStats stats) {
		try {
			statsDAO.updateGameStats(game, stats);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the yearly stats for a given player
	 *
	 * @param player The player to fetch stats for
	 * @param year The year to collect stats for
	 * @return The optional player stats
	 */
	public Optional<PlayerStats> getPlayerYearlyStats(Player player, Integer year) {
		try {
			return statsDAO.getPlayerYearlyStats(player, year);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
