package com.grahamsfault.nfl.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileGameDAO implements GameDAO {

	private static final String DEFAULT_FILENAME = "assets/db/schedule.json";

	private final ObjectMapper mapper;
	private final NflService gameService;
	private final Client client;

	public FileGameDAO(ObjectMapper mapper, NflService gameService) {
		this.mapper = mapper;
		this.gameService = gameService;
		client = ClientBuilder.newClient();
	}

	@Override
	public List<Game> searchGames(
			int year,
			GameType gameType,
			Optional<Integer> week,
			Optional<Team> home,
			Optional<Team> away
	) {
		List<Game> allGames = allGames();

		return allGames.stream()
				.filter(game -> game.getYear() == year)
				.filter(game -> game.getSeasonType() == gameType)
				.filter(game -> !week.isPresent() || game.getWeek() == week.get())
				.filter(game -> !home.isPresent() || game.getHome() == home.get())
				.filter(game -> !away.isPresent() || game.getAway() == away.get())
				.collect(Collectors.toList());
	}

	@Override
	public GameStatsWrapper gameStats(String eid) {
		return gameService.gameStats(eid);
	}

	/**
	 * Get all the games
	 */
	protected List<Game> allGames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(DEFAULT_FILENAME).getFile());
		try {
			return mapper.readValue(file, new TypeReference<List<Game>>() {});
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
