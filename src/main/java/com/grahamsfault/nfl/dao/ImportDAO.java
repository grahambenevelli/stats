package com.grahamsfault.nfl.dao;


import java.sql.SQLException;

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
}
