package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.model.Game;
import com.grahamsfault.nfl.model.Team;
import com.grahamsfault.nfl.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.model.game.GameType;

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

	GameStatsWrapper gameStats(String eid);
}
