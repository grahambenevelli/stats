package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.model.Game;
import com.grahamsfault.nfl.model.Team;
import com.grahamsfault.nfl.model.game.GameNotes;
import com.grahamsfault.nfl.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.model.game.GameType;

import java.util.List;
import java.util.Optional;

public class GameManager {

	private final GameDAO gameDAO;

	public GameManager(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

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

	public Optional<GameNotes> gameStats(String eid) {
		GameStatsWrapper gameStatsWrapper = gameDAO.gameStats(eid);
		if (!gameStatsWrapper.getProfiles().containsKey(eid)) {
			return Optional.empty();
		}
		return Optional.of(gameStatsWrapper.getProfiles().get(eid));
	}
}
