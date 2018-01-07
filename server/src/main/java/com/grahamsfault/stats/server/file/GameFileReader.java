package com.grahamsfault.stats.server.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.api.model.Game;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Use the game list file until we can manually get the ids from the site
 */
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
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			// TODO read file differently
			File file = new File(classLoader.getResource(DEFAULT_FILENAME).getFile());
			return mapper.readValue(file, new TypeReference<List<Game>>() {});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
