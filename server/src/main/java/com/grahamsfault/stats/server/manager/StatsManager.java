package com.grahamsfault.stats.server.manager;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.impl.Tuple;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.dao.StatsDAO;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.sql.SQLException;
import java.util.Map;
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

	/**
	 * Get the Stddev of each stat by position
	 *
	 * @return A map of position to StdDevStats
	 */
	public Map<Position, StdDevStats> getStdDevByPosition() {
		try {
			return statsDAO.getStdDevByPosition();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get yearly stats for a given position, divide it up by player and year combination
	 *
	 * @param position The position to fetch stats for
	 * @param year The year to fetch for
	 * @return A map of player/year to player's stats for that year
	 */
	public Map<Tuple<Player, Year>, PlayerStats> getYearlyPositionStats(Position position, Integer year) {
		try {
			return statsDAO.getYearlyPositionStats(position, year);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the Stddev for a given position
	 *
	 * @param position The position to fetch for
	 * @return the StdDevStats object
	 */
	public StdDevStats getStdDevForPosition(Position position) {
		return this.getStdDevByPosition().get(position);
	}
}
