package com.grahamsfault.nfl.dao;


import com.grahamsfault.nfl.model.GameImportLog;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ImportDAO {

	/**
	 * Check to see if the give game has already been imported
	 *
	 * @param gameId The game id
	 * @return true if it has been imported, false if not
	 * @throws SQLException
	 */
	boolean hasImported(String gameId) throws SQLException;

	/**
	 * Mark the given game as imported
	 *
	 * @param gameId The game id
	 * @throws SQLException
	 */
	void markAsImported(String gameId) throws SQLException;

	/**
	 * Get the years we have stats for
	 *
	 * @return The list of years available
	 */
	List<Integer> getYears() throws SQLException;

	/**
	 * Get the compile the yearly stats into a table
	 *
	 * @param year The year to compile stats for
	 */
	void compileYearlyStats(int year) throws SQLException;

	/**
	 * Record a player id for import
	 *
	 * @param year The year we have looked through
	 * @param gsisId The id of the player according to nfl.com
	 * @throws SQLException
	 */
	void recordPlayerIdForImport(int year, String gsisId) throws SQLException;

	/**
	 * Mark the game has having imported the player ids
	 *
	 * @param eid The game id according to nfl.com
	 * @throws SQLException
	 */
	void markPlayerIdsAsImported(String eid) throws SQLException;

	/**
	 * Get the import log for a given game
	 *
	 * @param eid The if of the game to fetch
	 * @return An optional GameImportLog
	 * @throws SQLException
	 */
	Optional<GameImportLog> getImportLog(String eid) throws SQLException;

	/**
	 * Get the players ids that are needed to be imported
	 *
	 * @return The list of player ids
	 * @throws SQLException
	 */
	List<String> getPlayerIdsForImport() throws SQLException;

	/**
	 * Mark the player id as having imported the basic info
	 *
	 * @param playerId The id of the player to mark
	 */
	void markPlayerBasicInfoImported(String playerId) throws SQLException;
}
