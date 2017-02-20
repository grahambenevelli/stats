package com.grahamsfault.nfl.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.model.Player;
import com.grahamsfault.nfl.model.Position;
import com.grahamsfault.nfl.model.Team;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FilePlayerDAOTest {

	private FilePlayerDAO playerDAO;

	@BeforeClass
	public void setup() {
		this.playerDAO = new FilePlayerDAO(new ObjectMapper());
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
				2499355,
				new URL("http://www.nfl.com/player/flozelladams/2499355/profile"),
				79,
				338,
				13,
				null,
				null,
				null,
				null
		);

		Optional<Player> first = players.stream().findFirst();

		assertTrue(first.isPresent());
		assertEquals(first.get(), expected);
	}

	@Test
	public void testSearchPlayer() throws MalformedURLException {
		Set<Player> players = playerDAO.searchForPlayer("Andre", "Johnson");

		Player expected = new Player(
				"7/11/1981",
				"Miami (Fla.)",
				"Andre",
				"Johnson",
				"Andre Johnson",
				"00-0022044",
				"A.Johnson",
				2505551,
				new URL("http://www.nfl.com/player/andrejohnson/2505551/profile"),
				75,
				229,
				14,
				81,
				"ACT",
				Team.TENNESSEE,
				Position.WR
		);

		Optional<Player> first = players.stream().findFirst();

		assertTrue(first.isPresent());
		assertEquals(first.get(), expected);
	}
}