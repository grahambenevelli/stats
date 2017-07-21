package com.grahamsfault.nfl.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.dao.GameDAO;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameFileReader {

	private static final String DEFAULT_FILENAME = "assets/db/schedule.json";

	private final ObjectMapper mapper;

	public GameFileReader(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * Get all games that we have a record of
	 */
	public List<Game> allGames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(DEFAULT_FILENAME).getFile());
		try {
			return mapper.readValue(file, new TypeReference<List<Game>>() {});
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
