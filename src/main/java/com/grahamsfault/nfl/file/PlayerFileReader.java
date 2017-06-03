package com.grahamsfault.nfl.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.grahamsfault.nfl.api.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Until we have a working database to back this, we are using a json file as the source of players
 */
public class PlayerFileReader {

	/**
	 * The json file holding all the players
	 */
	private static final String DEFAULT_FILENAME = "assets/db/players.json";

	private final ObjectMapper mapper;

	public PlayerFileReader(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	/**
	 * Returns a predicate statement that matches a player to first and last name if included
	 */
	private Predicate<Player> getPlayerPredicate(Optional<String> firstName, Optional<String> lastName) {
		return player -> {
			if (firstName.isPresent() && !player.getFirstName().equalsIgnoreCase(firstName.get())) {
				return false;
			}

			if (lastName.isPresent() && !player.getLastName().equalsIgnoreCase(lastName.get())) {
				return false;
			}

			return true;
		};
	}

	/**
	 * Get a list of all the players
	 */
	public List<Player> allPlayers() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(DEFAULT_FILENAME).getFile());
		try {
			Map<String, Player> map = mapper.readValue(file, new TypeReference<Map<String, Player>>() {});
			return map.entrySet()
					.stream()
					.map(Map.Entry::getValue)
					.collect(Collectors.<Player>toList());
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}
