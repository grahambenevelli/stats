package com.grahamsfault.nfl.dao;


import java.sql.SQLException;
import java.util.List;

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
}
