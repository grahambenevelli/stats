package com.grahamsfault.nfl.stats.manager;

import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.dao.StatsDAO;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import java.sql.SQLException;
import java.util.Optional;

public class StatsManager {

	private final StatsDAO statsDAO;

	public StatsManager(StatsDAO statsDAO) {
		this.statsDAO = statsDAO;
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
