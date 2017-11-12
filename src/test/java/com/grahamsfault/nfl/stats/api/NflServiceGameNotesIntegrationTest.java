package com.grahamsfault.nfl.stats.api;

import com.google.common.collect.Lists;
import com.grahamsfault.nfl.stats.api.exception.NflServiceException;
import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.Team;
import com.grahamsfault.nfl.stats.api.model.game.GameNotes;
import com.grahamsfault.nfl.stats.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.stats.api.model.game.GameType;
import com.grahamsfault.nfl.stats.api.model.game.drive.Drive;
import com.grahamsfault.nfl.stats.api.model.game.drive.GameDrives;
import com.grahamsfault.nfl.stats.api.model.game.drive.play.PlayEntry;
import com.grahamsfault.nfl.stats.api.model.game.drive.play.PlayPlayerStatEntry;
import com.grahamsfault.nfl.stats.api.model.game.drive.play.PlayPlayersWrapper;
import com.grahamsfault.nfl.stats.api.model.game.team.GameTeamNotes;
import com.grahamsfault.nfl.stats.api.model.game.team.QuarterlyScores;
import com.grahamsfault.nfl.stats.api.model.player.PlayerStatsSet;
import com.grahamsfault.nfl.stats.api.model.player.RawStats;
import com.grahamsfault.nfl.stats.api.model.team.TeamStats;
import com.grahamsfault.nfl.stats.factory.StatsApplicationFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NflServiceGameNotesIntegrationTest {

	private NflService nflService;
	private GameNotes gameNotes;

	@BeforeMethod
	public void setup() throws NflServiceException {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		nflService = factory.getNflService();
		gameNotes = this.nflService.getGameNotes("2013090500");
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

	@Test
	public void testGetGameStatsBasicGameNotes() throws NflServiceException {
		assertEquals(gameNotes.getWeather(), null);
		assertEquals(gameNotes.getMedia(), null);
		assertEquals(gameNotes.getYl(), "");
		assertEquals(gameNotes.getQtr(), "Final");
		assertEquals(gameNotes.getNote(), null);
		assertEquals(gameNotes.getDown().intValue(), 0);
		assertEquals(gameNotes.getTogo().intValue(), 0);
		assertEquals(gameNotes.isRedzone(), true);
		assertEquals(gameNotes.getClock(), "00:39");
		assertEquals(gameNotes.getPosteam(), Team.DENVER);
		assertEquals(gameNotes.getStadium(), null);
	}

	@Test
	public void testGetGameStatsAway() throws NflServiceException {
		GameTeamNotes actual = gameNotes.getAway();

		GameTeamNotes expected = new GameTeamNotes(
				new QuarterlyScores(
						7,
						10,
						0,
						10,
						0,
						27
				),
				Team.BALTIMORE,
				new TeamStats(
						new PlayerStatsSet()
								.setDynamicProperty("00-0026158", new RawStats(
										"J.Flacco",
										62,
										34,
										null,
										null,
										null,
										null,
										null,
										362,
										2,
										2,
										null,
										null,
										0,
										0
								)),
						new PlayerStatsSet()
								.setDynamicProperty("00-0026195", new RawStats(
										"R.Rice",
										12,
										null,
										null,
										null,
										null,
										null,
										null,
										36,
										1,
										null,
										12,
										1,
										0,
										0
								))
								.setDynamicProperty("00-0029264", new RawStats(
										"B.Pierce",
										9	,
										null,
										null,
										null,
										null,
										null,
										null,
										22,
										0,
										null,
										14,
										0,
										0,
										0
								)),
						new PlayerStatsSet()
								.setDynamicProperty("00-0026195", new RawStats(
										"R.Rice",
										null,
										null,
										8,
										null,
										null,
										null,
										null,
										35,
										0,
										null,
										10,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0022165", new RawStats(
										"D.Clark",
										null,
										null,
										7,
										null,
										null,
										null,
										null,
										87,
										0,
										null,
										31,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0027996", new RawStats(
										"T.Smith",
										null,
										null,
										4,
										null,
										null,
										null,
										null,
										92,
										0,
										null,
										34,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0030400", new RawStats(
										"M.Brown",
										null,
										null,
										4,
										null,
										null,
										null,
										null,
										65,
										1,
										null,
										23,
										13,
										0,
										0
								))
								.setDynamicProperty("00-0015754", new RawStats(
										"B.Stokley",
										null,
										null,
										4,
										null,
										null,
										null,
										null,
										34,
										0,
										null,
										12,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0025460", new RawStats(
										"J.Jones",
										null,
										null,
										3,
										null,
										null,
										null,
										null,
										24,
										0,
										null,
										13,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0022397", new RawStats(
										"V.Leach",
										null,
										null,
										3,
										null,
										null,
										null,
										null,
										12,
										1,
										null,
										6,
										2,
										0,
										0
								))
								.setDynamicProperty("00-0027675", new RawStats(
										"E.Dickson",
										null,
										null,
										1,
										null,
										null,
										null,
										null,
										13,
										0,
										null,
										13,
										0,
										0,
										0
								)),
						new PlayerStatsSet()
								.setDynamicProperty("00-0026158", new RawStats(
										"J.Flacco",
										null,
										null,
										null,
										1,
										0,
										0,
										0,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
								.setDynamicProperty("00-0029893", new RawStats(
										"R.Wagner",
										null,
										null,
										null,
										0,
										1,
										1,
										0,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
								.setDynamicProperty("00-0027557", new RawStats(
										"M.Cox",
										null,
										null,
										null,
										0,
										0,
										1,
										0,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
				)
		);

		assertEquals(actual, expected);
	}

	@Test
	public void testGetGameStatsHome() {
		GameTeamNotes actual = gameNotes.getHome();

		GameTeamNotes expected = new GameTeamNotes(
				new QuarterlyScores(
						0,
						14,
						21,
						14,
						0,
						49
				),
				Team.DENVER,
				new TeamStats(
						new PlayerStatsSet()
								.setDynamicProperty("00-0010346", new RawStats(
										"P.Manning",
										42,
										27,
										null,
										null,
										null,
										null,
										null,
										462,
										7,
										0,
										null,
										null,
										0,
										0
								).setPlayerId("00-0010346")),
						new PlayerStatsSet()
								.setDynamicProperty("00-0026988", new RawStats(
										"K.Moreno",
										9,
										null,
										null,
										null,
										null,
										null,
										null,
										28,
										0,
										null,
										7,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0030522", new RawStats(
										"M.Ball",
										8,
										null,
										null,
										null,
										null,
										null,
										null,
										24,
										0,
										null,
										9,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0029683", new RawStats(
										"R.Hillman",
										4,
										null,
										null,
										null,
										null,
										null,
										null,
										15,
										0,
										null,
										7,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0010346", new RawStats(
										"P.Manning",
										2,
										null,
										null,
										null,
										null,
										null,
										null,
										-2,
										0,
										null,
										-1,
										0,
										0,
										0
								)),
						new PlayerStatsSet()
								.setDynamicProperty("00-0022427", new RawStats(
										"W.Welker",
										null,
										null,
										9,
										null,
										null,
										null,
										null,
										67,
										2,
										null,
										15,
										5,
										0,
										0
								))
								.setDynamicProperty("00-0027874", new RawStats(
										"D.Thomas",
										null,
										null,
										5,
										null,
										null,
										null,
										null,
										161,
										2,
										null,
										78,
										78,
										0,
										0
								))
								.setDynamicProperty("00-0028067", new RawStats(
										"J.Thomas",
										null,
										null,
										5,
										null,
										null,
										null,
										null,
										110,
										2,
										null,
										44,
										24,
										0,
										0
								))
								.setDynamicProperty("00-0026988", new RawStats(
										"K.Moreno",
										null,
										null,
										3,
										null,
										null,
										null,
										null,
										37,
										0,
										null,
										23,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0027690", new RawStats(
										"E.Decker",
										null,
										null,
										2,
										null,
										null,
										null,
										null,
										32,
										0,
										null,
										27,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0029683", new RawStats(
										"R.Hillman",
										null,
										null,
										2,
										null,
										null,
										null,
										null,
										27,
										0,
										null,
										17,
										0,
										0,
										0
								))
								.setDynamicProperty("00-0026237", new RawStats(
										"A.Caldwell",
										null,
										null,
										1,
										null,
										null,
										null,
										null,
										28,
										1,
										null,
										28,
										28,
										0,
										0
								)),
						new PlayerStatsSet()
								.setDynamicProperty("00-0022427", new RawStats(
										"W.Welker",
										null,
										null,
										null,
										1,
										0,
										0,
										1,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
								.setDynamicProperty("00-0027690", new RawStats(
										"E.Decker",
										null,
										null,
										null,
										1,
										0,
										0,
										0,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
								.setDynamicProperty("00-0029577", new RawStats(
										"D.Trevathan",
										null,
										null,
										null,
										1,
										0,
										0,
										1,
										0,
										null,
										null,
										null,
										null,
										null,
										null
								))
				)
		);

		assertEquals(actual, expected);
	}

	@Test
	public void testGetGameStatsGameDrivesBasic() {
		GameDrives actual = gameNotes.getDrives();

		assertEquals(actual.getCrntdrv(), 33);
	}

	@Test
	public void testGetGameStatsFirstDriveBasic() {
		Drive actual = gameNotes.getDrives().getDrives().get(0);

		assertEquals(actual.getPosteam(), Team.BALTIMORE);
		assertEquals(actual.getQuarter().intValue(), 1);
		assertEquals(actual.isRedzone(), true);
		assertEquals(actual.getFds().intValue(), 0);
		assertEquals(actual.getResult(), "Punt");
		assertEquals(actual.getPenyds().intValue(), 0);
		assertEquals(actual.getYdsgained().intValue(), 8);
		assertEquals(actual.getNumplays().intValue(), 5);
		assertEquals(actual.getPostime(), "1:50");
	}

	@Test
	public void testGetGameStatsFirstDrivesFirstPlayBasic() {
		PlayEntry actual = gameNotes.getDrives().getDrives().get(0).getPlays().getPlays().get(0);

		assertEquals(actual.getSp().intValue(), 0);
		assertEquals(actual.getQtr().intValue(), 1);
		assertEquals(actual.getDown().intValue(), 0);
		assertEquals(actual.getTime(), "15:00");
		assertEquals(actual.getYardLine(), "DEN 35");
		assertEquals(actual.getYardsTogo().intValue(), 0);
		assertEquals(actual.getYardsNet().intValue(), 0);
		assertEquals(actual.getPosteam(), Team.DENVER);
		assertEquals(actual.getDesc(), "M.Prater kicks 65 yards from DEN 35 to end zone, Touchback.");
		assertEquals(actual.getNote(), "KICKOFF");
	}

	@Test
	public void testGetGameStatsFirstDrivesFirstPlayPlayersStats() {
		PlayEntry actual = gameNotes.getDrives().getDrives().get(0).getPlays().getPlays().get(0);
		PlayPlayersWrapper players = actual.getPlayers();

		Map<String, List<PlayPlayerStatEntry>> playersMap = players.getPlayersMap();
		assertTrue(playersMap.containsKey("0"));
		assertEquals(playersMap.get("0"), Lists.newArrayList(
				new PlayPlayerStatEntry(
						2,
						Team.BALTIMORE,
						null,
						51,
						0
				)
		));
		assertTrue(playersMap.containsKey("00-0023853"));
		assertEquals(playersMap.get("00-0023853"), Lists.newArrayList(
				new PlayPlayerStatEntry(
						1,
						Team.DENVER,
						"M.Prater",
						44,
						65
				),
				new PlayPlayerStatEntry(
						3,
						Team.DENVER,
						"M.Prater",
						410,
						75
				)
		));
	}

	@Test
	public void testGetGameStatsSecondDrivesFirstPlayBasic() {
		PlayEntry actual = gameNotes.getDrives().getDrives().get(0).getPlays().getPlays().get(1);

		assertEquals(actual.getSp().intValue(), 0);
		assertEquals(actual.getQtr().intValue(), 1);
		assertEquals(actual.getDown().intValue(), 1);
		assertEquals(actual.getTime(), "15:00");
		assertEquals(actual.getYardLine(), "BAL 20");
		assertEquals(actual.getYardsTogo().intValue(), 10);
		assertEquals(actual.getYardsNet().intValue(), 8);
		assertEquals(actual.getPosteam(), Team.BALTIMORE);
		assertEquals(actual.getDesc(), "(15:00) J.Flacco pass short middle to R.Rice to BAL 19 for -1 yards (W.Woodyard).");
		assertEquals(actual.getNote(), null);
	}

	@Test
	public void testGetGameStatsSecondDrivesFirstPlayPlayersStats() {
		PlayEntry actual = gameNotes.getDrives().getDrives().get(0).getPlays().getPlays().get(1);
		PlayPlayersWrapper players = actual.getPlayers();

		Map<String, List<PlayPlayerStatEntry>> playersMap = players.getPlayersMap();
		assertTrue(playersMap.containsKey("00-0025828"));
		assertEquals(playersMap.get("00-0025828"), Lists.newArrayList(
				new PlayPlayerStatEntry(
						1,
						Team.DENVER,
						"W.Woodyard",
						402,
						1
				),
				new PlayPlayerStatEntry(
						5,
						Team.DENVER,
						"W.Woodyard",
						120,
						0
				),
				new PlayPlayerStatEntry(
						6,
						Team.DENVER,
						"W.Woodyard",
						79,
						0
				)
		));

		assertTrue(playersMap.containsKey("00-0026158"));
		assertEquals(playersMap.get("00-0026158"), Lists.newArrayList(
				new PlayPlayerStatEntry(
						3,
						Team.BALTIMORE,
						"J.Flacco",
						111,
						-2
				),
				new PlayPlayerStatEntry(
						8,
						Team.BALTIMORE,
						"J.Flacco",
						15,
						-1
				)
		));

		assertTrue(playersMap.containsKey("00-0026195"));
		assertEquals(playersMap.get("00-0026195"), Lists.newArrayList(
				new PlayPlayerStatEntry(
						2,
						Team.BALTIMORE,
						"R.Rice",
						21,
						-1
				),
				new PlayPlayerStatEntry(
						4,
						Team.BALTIMORE,
						"R.Rice",
						115,
						0
				),
				new PlayPlayerStatEntry(
						7,
						Team.BALTIMORE,
						"R.Rice",
						113,
						1
				)
		));
	}
}