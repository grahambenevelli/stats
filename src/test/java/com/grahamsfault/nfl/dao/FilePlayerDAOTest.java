package com.grahamsfault.nfl.dao;

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
				2499355L,
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

	@DataProvider
	public static Object[][] searchPlayerDataProvider() throws Exception {
		return new Object[][]{
				{
						"Andre",
						"Johnson",
						Optional.of(new Player(
								"7/11/1981",
								"Miami (Fla.)",
								"Andre",
								"Johnson",
								"Andre Johnson",
								"00-0022044",
								"A.Johnson",
								2505551L,
								new URL("http://www.nfl.com/player/andrejohnson/2505551/profile"),
								75,
								229,
								14,
								81,
								"ACT",
								Team.TENNESSEE,
								Position.WR
						))
				},
				{
						"Braxton",
						"Miller",
						Optional.of(new Player(
								"11/30/1992",
								"Ohio State",
								"Braxton",
								"Miller",
								"Braxton Miller",
								"00-0033069",
								null,
								2555348L,
								new URL("http://www.nfl.com/player/braxtonmiller/2555348/profile"),
								73,
								201,
								0,
								13,
								"ACT",
								Team.HOUSTON,
								Position.WR
						))
				},
				{
						null,
						"Miller",
						Optional.of(new Player(
								"4/24/1990",
								"Virginia Tech",
								"Andrew",
								"Miller",
								"Andrew Miller",
								"00-0030837",
								null,
								2550497L,
								new URL("http://www.nfl.com/player/andrewmiller/2550497/profile"),
								76,
								296,
								1,
								0,
								null,
								null,
								null
						))
				},
		};
	}

	@Test(dataProvider = "searchPlayerDataProvider")
	public void testSearchPlayer(String firstName, String lastName, Optional<Player> expected) throws Exception {
		Set<Player> players = playerDAO.searchForPlayer(Optional.ofNullable(firstName), Optional.ofNullable(lastName));

		Optional<Player> first = players.stream().findFirst();

		if (expected.isPresent()) {
			assertTrue(first.isPresent());
			assertEquals(first.get(), expected.get());
		} else {
			assertTrue(!first.isPresent());
		}
	}
}