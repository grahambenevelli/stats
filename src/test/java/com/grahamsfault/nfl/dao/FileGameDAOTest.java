package com.grahamsfault.nfl.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.model.Game;
import com.grahamsfault.nfl.model.Team;
import com.grahamsfault.nfl.model.game.GameType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FileGameDAOTest {

	private FileGameDAO gameDAO;

	@BeforeMethod
	public void setup() {
		gameDAO = new FileGameDAO(new ObjectMapper());
	}

	@DataProvider
	public static Object[][] searchGameDataProvider() throws Exception {
		return new Object[][]{
				{
						2009,
						GameType.PRE,
						0,
						Team.TENNESSEE,
						Team.BUFFALO,
						1,
						new Game(
								Team.BUFFALO,
								Team.TENNESSEE,
								9,
								"2009080950",
								"54723",
								null,
								8,
								GameType.PRE,
								"8:00",
								"Sun",
								0,
								2009
						)
				},
				{
						2009,
						GameType.REG,
						1,
						Team.PITTSBURGH,
						Team.TENNESSEE,
						1,
						new Game(
								Team.TENNESSEE,
								Team.PITTSBURGH,
								10,
								"2009091000",
								"54467",
								null,
								9,
								GameType.REG,
								"8:30",
								"Thu",
								1,
								2009
						)
				},
				{
						2009,
						GameType.REG,
						1,
						Team.PITTSBURGH,
						null,
						1,
						new Game(
								Team.TENNESSEE,
								Team.PITTSBURGH,
								10,
								"2009091000",
								"54467",
								null,
								9,
								GameType.REG,
								"8:30",
								"Thu",
								1,
								2009
						)
				},
				{
						2009,
						GameType.REG,
						1,
						null,
						Team.TENNESSEE,
						1,
						new Game(
								Team.TENNESSEE,
								Team.PITTSBURGH,
								10,
								"2009091000",
								"54467",
								null,
								9,
								GameType.REG,
								"8:30",
								"Thu",
								1,
								2009
						)
				},
				{
						2009,
						GameType.REG,
						null,
						Team.PITTSBURGH,
						null,
						8,
						new Game(
								Team.TENNESSEE,
								Team.PITTSBURGH,
								10,
								"2009091000",
								"54467",
								null,
								9,
								GameType.REG,
								"8:30",
								"Thu",
								1,
								2009
						)
				},
		};
	}

	@Test(dataProvider = "searchGameDataProvider")
	public void testSearchGames(
			int year,
			GameType gameType,
			Integer week,
			Team home,
			Team away,
			int size,
			Game expected
	) throws Exception {
		List<Game> actual = gameDAO.searchGames(
				year,
				gameType,
				Optional.ofNullable(week),
				Optional.ofNullable(home),
				Optional.ofNullable(away)
		);

		assertEquals(actual.size(), size);
		if (size > 0) {
			assertEquals(actual.stream().findFirst().get(), expected);
		}
	}

	@Test
	public void testAllGames() throws Exception {
		List<Game> games = gameDAO.allGames();
		Optional<Game> actual = games.stream().findFirst();

		Game expected = new Game(
				Team.BUFFALO,
				Team.TENNESSEE,
				9,
				"2009080950",
				"54723",
				null,
				8,
				GameType.PRE,
				"8:00",
				"Sun",
				0,
				2009
		);

		assertTrue(actual.isPresent());
		assertEquals(actual.get(), expected);
	}
}