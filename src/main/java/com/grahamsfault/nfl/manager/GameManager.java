package com.grahamsfault.nfl.manager;

import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.dao.GameDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GameManager {

	private final GameDAO gameDAO;

	public GameManager(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	/**
	 * Search for games given the search criteria
	 *
	 * @param year The year of the game to search for
	 * @param gameType The game type of the game
	 * @param week The week of the game, optional
	 * @param home The home team, optional
	 * @param away The away team, optional
	 *
	 * @return A list of games that match this
	 */
	public List<Game> searchGames(
			int year,
			GameType gameType,
			Optional<Integer> week,
			Optional<Team> home,
			Optional<Team> away
	) {
		return gameDAO.searchGames(
				year,
				gameType,
				week,
				home,
				away
		);
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
			throw Throwables.propagate(e);
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
