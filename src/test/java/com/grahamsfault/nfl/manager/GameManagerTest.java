package com.grahamsfault.nfl.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.api.FileGameService;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameNotes;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GameManagerTest {

	private ObjectMapper mapper;
	private GameManager manager;

	@BeforeClass
	public void setup() {
		mapper = new ObjectMapper();
		manager = new GameManager(new FileGameService(mapper));
	}

	@Test
	public void testGameStats() throws Exception {
		Optional<GameNotes> gameStats = manager.gameStats("2013090500");

		assertTrue(gameStats.isPresent());

		GameNotes expected = new GameNotes(
				"Final",
				null,
				null,
				"",
				null,
				0,
				0,
				true,
				"00:39",
				Team.DENVER,
				null,
				null,
				null,
				null);
		assertEquals(gameStats.get(), expected);

		String s = mapper.writeValueAsString(gameStats.get());
		assertEquals(s, "");
	}
}