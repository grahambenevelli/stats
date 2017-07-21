package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GameDAO {

	/**
	 * Searchh for games given the search criteria
	 *
	 * @param year The year of the game to search for
	 * @param gameType The game type of the game
	 * @param week The week of the game, optional
	 * @param home The home team, optional
	 * @param away The away team, optional
	 *
	 * @return A list of games that match this
	 */
	List<Game> searchGames(
			int year,
			GameType gameType,
			Optional<Integer> week,
			Optional<Team> home,
			Optional<Team> away
	);

	/**
	 * Get the game stats from a game id
	 *
	 * @param eid The id of the game
	 * @return The wrapper class for the game stats
	 */
	GameStatsWrapper gameStats(String eid);

	/**
	 * Update the game in the database
	 *
	 * @param game The game object to update
	 */
	void updateGame(Game game) throws SQLException;

	/**
	 * Get the game by id
	 *
	 * @param id The id of the game
	 * @return On optional version of the game
	 * @throws SQLException
	 */
	Optional<Game> getById(String id) throws SQLException;
}
