package com.grahamsfault.nfl.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.grahamsfault.nfl.model.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePlayerDAO extends BaseDAO implements PlayerDAO {

	private static final String DEFAULT_FILENAME = "assets/db/players.json";

	private final ObjectMapper mapper;

	public FilePlayerDAO(ObjectMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public Set<Player> searchForPlayer(final String firstName, final String lastName) {
		List<Player> allPlayers = allPlayers();
		Stream<Player> playerStream = allPlayers
				.stream()
				.filter(getPlayerPredicate(firstName, lastName));

		return playerStream.collect(Collectors.<Player>toSet());
	}

	private Predicate<Player> getPlayerPredicate(String firstName, String lastName) {
		return player -> {
			if (firstName != null && !player.getFirstName().equals(firstName)) {
				return false;
			}

			if (lastName != null && !player.getLastName().equals(lastName)) {
				return false;
			}

			return true;
		};
	}

	protected List<Player> allPlayers() {
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
