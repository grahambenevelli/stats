package com.grahamsfault.nfl.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.nfl.dao.FileGameDAO;
import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.manager.builder.GameStatsBuilder;
import com.grahamsfault.nfl.model.GameStats;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GameManagerTest {

	private ObjectMapper mapper;
	private GameDAO gameDAO;
	private GameManager manager;

	@BeforeClass
	public void setup() {
		mapper = new ObjectMapper();
		gameDAO = new FileGameDAO(mapper, new NflService(mapper));
		manager = new GameManager(gameDAO);
	}

	@Test
	public void testGameStats() throws Exception {
		Optional<GameStats> gameStats = manager.gameStats("2013090500");

		assertTrue(gameStats.isPresent());

		GameStatsBuilder builder = new GameStatsBuilder();

		addJFlacco(builder);
		addRRice(builder);
		addPierce(builder);
		addTSmith(builder);
		addDClark(builder);
		addMBrown(builder);
		addBStokley(builder);
		addJJones(builder);
		addEDickson(builder);
		addVLeach(builder);
		addMCox(builder);
		addRWagner(builder);
		addPManning(builder);
		addKMoreno(builder);
		addMBall(builder);
		addRHillman(builder);
		addDThomas(builder);
		addJThomas(builder);
		addWWelker(builder);
		addEDecker(builder);
		addACaldwell(builder);
		addDTrevathan(builder);

		GameStats expected = builder.build();
		GameStats actual = gameStats.get();

		String key;

		key = "00-0029577";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0026237";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0027690";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0022427";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0028067";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0027874";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0029683";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0030522";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0026988";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0010346";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0029893";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0027557";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0022397";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0027675";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0025460";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0015754";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0030400";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0022165";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0027996";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0029264";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0026195";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));
		key = "00-0026158";
		assertEquals(actual.getPlayerStatsMap().get(key), expected.getPlayerStatsMap().get(key));

		assertEquals(actual, expected);
	}

	private void addDTrevathan(GameStatsBuilder builder) {
		builder.addFumbles("00-0029577", new RawStats(
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
		));
	}

	private void addACaldwell(GameStatsBuilder builder) {
		builder.addReceiving("00-0026237", new RawStats(
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
		));
	}

	private void addEDecker(GameStatsBuilder builder) {
		builder.addReceiving("00-0027690", new RawStats(
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
		));

		builder.addFumbles("00-0027690", new RawStats(
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
		));
	}

	private void addWWelker(GameStatsBuilder builder) {
		builder.addReceiving("00-0022427", new RawStats(
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
		));

		builder.addFumbles("00-0022427", new RawStats(
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
		));
	}

	private void addJThomas(GameStatsBuilder builder) {
		builder.addReceiving("00-0028067", new RawStats(
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
		));
	}

	private void addDThomas(GameStatsBuilder builder) {
		builder.addReceiving("00-0027874", new RawStats(
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
		));
	}

	private void addRHillman(GameStatsBuilder builder) {
		builder.addRushing("00-0029683", new RawStats(
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
		));

		builder.addReceiving("00-0029683", new RawStats(
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
		));
	}

	private void addMBall(GameStatsBuilder builder) {
		builder.addRushing("00-0030522", new RawStats(
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
		));
	}

	private void addKMoreno(GameStatsBuilder builder) {
		builder.addRushing("00-0026988", new RawStats(
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
		));

		builder.addReceiving("00-0026988", new RawStats(
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
		));
	}

	private void addPManning(GameStatsBuilder builder) {
		builder.addPassing("00-0010346", new RawStats(
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
		));

		builder.addRushing("00-0010346", new RawStats(
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
		));
	}

	private void addRWagner(GameStatsBuilder builder) {
		builder.addFumbles("00-0029893", new RawStats(
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
		));
	}

	private void addMCox(GameStatsBuilder builder) {
		builder.addFumbles("00-0027557", new RawStats(
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
		));
	}

	private void addVLeach(GameStatsBuilder builder) {
		builder.addReceiving("00-0022397", new RawStats(
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
		));
	}

	private void addEDickson(GameStatsBuilder builder) {
		builder.addReceiving("00-0027675", new RawStats(
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
		));
	}

	private void addJJones(GameStatsBuilder builder) {
		builder.addReceiving("00-0025460", new RawStats(
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
		));
	}

	private void addBStokley(GameStatsBuilder builder) {
		builder.addReceiving("00-0015754", new RawStats(
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
		));
	}

	private void addMBrown(GameStatsBuilder builder) {
		builder.addReceiving("00-0030400", new RawStats(
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
		));
	}

	private void addDClark(GameStatsBuilder builder) {
		builder.addReceiving("00-0022165", new RawStats(
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
		));
	}

	private void addTSmith(GameStatsBuilder builder) {
		builder.addReceiving("00-0027996", new RawStats(
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
		));
	}

	private void addPierce(GameStatsBuilder builder) {
		builder.addRushing("00-0029264", new RawStats(
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
		));
	}

	private void addRRice(GameStatsBuilder builder) {
		String key = "00-0026195";
		builder.addRushing(key, new RawStats(
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
		));

		builder.addReceiving(key, new RawStats(
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
		));
	}

	private void addJFlacco(GameStatsBuilder builder) {
		builder.addPassing("00-0026158", new RawStats(
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
		));

		builder.addFumbles("00-0026158", new RawStats(
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
		));
	}
}