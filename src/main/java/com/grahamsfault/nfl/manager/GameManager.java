package com.grahamsfault.nfl.manager;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameNotes;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.manager.builder.GameStatsBuilder;
import com.grahamsfault.nfl.model.GameStats;

import java.util.List;
import java.util.Map;
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

	public Optional<GameStats> gameStats(String eid) {
		GameStatsWrapper gameStatsWrapper = gameDAO.gameStats(eid);
		if (!gameStatsWrapper.getProfiles().containsKey(eid)) {
			return Optional.empty();
		}
		return Optional.of(convertToGameStats(gameStatsWrapper.getProfiles().get(eid)));
	}

	private GameStats convertToGameStats(GameNotes gameNotes) {
		GameStatsBuilder gameStatsFactory = new GameStatsBuilder();

		for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getPassing().getStats().entrySet()) {
			gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getRushing().getStats().entrySet()) {
			gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getReceiving().getStats().entrySet()) {
			gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getFumbles().getStats().entrySet()) {
			gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getPassing().getStats().entrySet()) {
			gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getRushing().getStats().entrySet()) {
			gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getReceiving().getStats().entrySet()) {
			gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getFumbles().getStats().entrySet()) {
			gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
		}

		return gameStatsFactory.build();
	}
}
