package com.grahamsfault.nfl.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.grahamsfault.nfl.model.Game;
import com.grahamsfault.nfl.model.Team;
import com.grahamsfault.nfl.model.game.GameType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileGameDAO implements GameDAO {

	private static final String DEFAULT_FILENAME = "assets/db/schedule.json";

	private final ObjectMapper mapper;

	public FileGameDAO(ObjectMapper mapper) {
		this.mapper = mapper;
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
