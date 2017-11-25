package com.grahamsfault.nfl.stats.manager;

import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.dao.GameDAO;

import java.sql.SQLException;
import java.util.Set;

/**
 * Manager class for game information
 */
public class GameManager {

	private final GameDAO gameDAO;

	public GameManager(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	/**
	 * Update or insert a game into the database
	 *
	 * @param game The game to update
	 */
	public void updateGame(Game game) {
		try {
			gameDAO.updateGame(game);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get all games recorded
	 *
	 * @return All the games recorded
	 */
	public Set<Game> allGames() {
		try {
			return gameDAO.allGames();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
