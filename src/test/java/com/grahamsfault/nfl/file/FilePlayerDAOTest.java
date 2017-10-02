package com.grahamsfault.nfl.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.player.Position;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FilePlayerDAOTest {

	private PlayerFileReader playerDAO;

	@BeforeClass
	public void setup() {
		this.playerDAO = new PlayerFileReader(new ObjectMapper());
	}

	@Test
	public void testGetAllPlayers() throws MalformedURLException {
		List<Player> players = this.playerDAO.allPlayers();

		Player expected = new Player(
				"5/18/1975",
				"Michigan State",
				"Flozell",
				"Adams",
				"Flozell Adams",
				"00-0000045",
				"F.Adams",
				2499355L,
				new URL("http://www.nfl.com/player/flozelladams/2499355/profile"),
				79,
				338,
				null,
				null,
				null,
				null,
				null,
				13,
				null
		);

		Optional<Player> first = players.stream().findFirst();

		assertTrue(first.isPresent());
		assertEquals(first.get(), expected);
	}
}