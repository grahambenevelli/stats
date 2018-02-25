package com.grahamsfault.stats.server.dao;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Year;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.impl.Tuple;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.sql.SQLException;
import java.util.Map;
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

	/**
	 * Get the std dev of stats for each position
	 *
	 * @return The map of Position to StdDevStats
	 */
	Map<Position,StdDevStats> getStdDevByPosition() throws SQLException;

	/**
	 * Get the yearly positional stats for each player
	 *
	 * @param position The position to fetch for
	 * @param year The year to fetch for
	 *
	 * @return A map of player/year to player stats
	 * @throws SQLException
	 */
	Map<Tuple<Player,Year>,PlayerStats> getYearlyPositionStats(Position position, Integer year) throws SQLException;
}
