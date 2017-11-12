package com.grahamsfault.nfl.stats.manager;

import com.grahamsfault.nfl.stats.dao.ImportDAO;
import com.grahamsfault.nfl.stats.model.GameImportLog;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Manager class for monitoring import information
 */
public class ImportManager {
	private final ImportDAO importDAO;

	public ImportManager(ImportDAO importDAO) {
		this.importDAO = importDAO;
	}

	/**
	 * Has the game been imported
	 *
	 * @param gameId The game id of the logs
	 * @return True if the game has been imported
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

	/**
	 * Record a player id that was just imported
	 *
	 * @param year The year to import
	 * @param gsisId The player id according to nfl.com
	 */
	public void recordPlayerIdForImport(int year, String gsisId) {
		try {
			importDAO.recordPlayerIdForImport(year, gsisId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Mark the player id as being imported
	 *
	 * @param eid The game id according to nfl.com
	 */
	public void markPlayerIdsAsImported(String eid) {
		try {
			importDAO.markPlayerIdsAsImported(eid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Check to see the player ids have been imported for a game
	 *
	 * @param eid The game id according to nfl.com
	 * @return true if the players have been imported
	 */
	public boolean hasPlayerIdsBeenImported(String eid) {
		try {
			Optional<GameImportLog> gameImportLog = importDAO.getImportLog(eid);
			if (gameImportLog.isPresent()) {
				return gameImportLog.get().hasPlayerIdImported();
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Ge the players id to import
	 *
	 * @return The list of player ids
	 */
	public List<String> getPlayerIds() {
		try {
			return importDAO.getPlayerIdsForImport();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Mark the player id as having imported the basic info
	 *
	 * @param playerId The player id
	 */
	public void markPlayerBasicInfoImported(String playerId) {
		try {
			importDAO.markPlayerBasicInfoImported(playerId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
