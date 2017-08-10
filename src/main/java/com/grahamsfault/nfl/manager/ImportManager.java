package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.dao.ImportDAO;
import org.apache.commons.lang3.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ImportManager {
	private final ImportDAO importDAO;

	public ImportManager(ImportDAO importDAO) {
		this.importDAO = importDAO;
	}

	/**
	 * Has the game been imported
	 *
	 * @param gameId The game id of the logs
	 * @return
	 */
	public boolean hasImported(String gameId) {
		try {
			return importDAO.hasImported(gameId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Mark the given game as imported
	 *
	 * @param gameId The game id
	 */
	public void markAsImported(String gameId) {
		try {
			importDAO.markAsImported(gameId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the years we have stats for
	 *
	 * @return The list of years available
	 */
	public List<Integer> getYears() {
		try {
			return importDAO.getYears();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Compile the yearly stats
	 *
	 * @param year The year to gather stats
	 */
	public void compileYearlyStats(int year) {
		try {
			importDAO.compileYearlyStats(year);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
