package com.grahamsfault.stats.server.manager.builder;

import com.google.common.collect.ImmutableMap;
import com.grahamsfault.stats.server.api.model.player.RawStats;
import com.grahamsfault.stats.server.model.GameStats;
import com.grahamsfault.stats.server.model.PlayerStats;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class GameStatsBuilderTest {

	private GameStatsBuilder builder;

	@BeforeMethod
	public void setup() {
		builder = new GameStatsBuilder();
	}

	@Test
	public void testTranslatePassingStats() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0026158";

		PlayerStats.Builder expected = PlayerStats.builder("J.Flacco", key)
				.passingAttempts(62)
				.passingCompletions(34)
				.passingYards(362)
				.passingTouchdowns(2)
				.interceptions(2);

		PlayerStats.Builder actual = builder.translatePassingStats(key, input);

		assertEquals(actual.build(), expected.build());
	}

	@Test
	public void testAddPassing() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0026158";

		PlayerStats playerStats = PlayerStats.builder("J.Flacco", key)
				.passingAttempts(62)
				.passingCompletions(34)
				.passingYards(362)
				.passingTouchdowns(2)
				.interceptions(2)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		builder.addPassing(key, input);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testAddRushingThenPassing() throws Exception {
		String key = "00-0000069";

		RawStats passingInput = new RawStats(
				"V.Young",
				40,
				30,
				null,
				null,
				null,
				null,
				null,
				267,
				0,
				0,
				45,
				0,
				null,
				null
		);
		builder.addPassing(key, passingInput);

		RawStats rushingInput = new RawStats(
				"V.Young",
				19,
				null,
				null,
				null,
				null,
				null,
				null,
				200,
				3,
				null,
				45,
				45,
				0,
				0
		);
		builder.addRushing(key, rushingInput);

		PlayerStats playerStats = PlayerStats.builder("V.Young", key)
				.passingAttempts(40)
				.passingCompletions(30)
				.passingYards(267)
				.passingTouchdowns(0)
				.interceptions(0)
				.rushingAttempts(19)
				.rushingYards(200)
				.rushingTouchdowns(3)
				.rushingLong(45)
				.rushingLongTouchdown(45)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testTranslateRushingStats() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0026195";

		PlayerStats expected = PlayerStats.builder("R.Rice", key)
				.rushingAttempts(12)
				.rushingYards(36)
				.rushingTouchdowns(1)
				.rushingLong(12)
				.rushingLongTouchdown(1)
				.build();

		PlayerStats.Builder actual = builder.translateRushingStats(key, input);

		assertEquals(actual.build(), expected);
	}

	@Test
	public void testAddRushing() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0026195";

		PlayerStats playerStats = PlayerStats.builder("R.Rice", key)
				.rushingAttempts(12)
				.rushingYards(36)
				.rushingTouchdowns(1)
				.rushingLong(12)
				.rushingLongTouchdown(1)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		builder.addRushing(key, input);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testAddPassingThenRushing() throws Exception {
		RawStats rushingInput = new RawStats(
				"V.Young",
				19,
				null,
				null,
				null,
				null,
				null,
				null,
				200,
				3,
				null,
				45,
				45,
				0,
				0
		);

		String key = "00-0000069";

		builder.addRushing(key, rushingInput);
		RawStats input = new RawStats(
				"V.Young",
				40,
				30,
				null,
				null,
				null,
				null,
				null,
				267,
				0,
				0,
				45,
				0,
				null,
				null
		);

		PlayerStats playerStats = PlayerStats.builder("V.Young", key)
				.passingAttempts(40)
				.passingCompletions(30)
				.passingYards(267)
				.passingTouchdowns(0)
				.interceptions(0)
				.rushingAttempts(19)
				.rushingYards(200)
				.rushingTouchdowns(3)
				.rushingLong(45)
				.rushingLongTouchdown(45)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		builder.addPassing(key, input);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testTranslateReceivingStats() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0030400";

		PlayerStats expected = PlayerStats.builder("M.Brown", key)
				.receptions(4)
				.receivingYards(65)
				.receivingTouchdowns(1)
				.receivingLong(23)
				.receivingLongTouchdown(13)
				.build();

		PlayerStats.Builder actual = builder.translateReceivingStats(key, input);

		assertEquals(actual.build(), expected);
	}

	@Test
	public void testAddReceiving() throws Exception {
		RawStats input = new RawStats(
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
		);

		String key = "00-0030400";

		PlayerStats playerStats = PlayerStats.builder("M.Brown", key)
				.receptions(4)
				.receivingYards(65)
				.receivingTouchdowns(1)
				.receivingLong(23)
				.receivingLongTouchdown(13)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		builder.addReceiving(key, input);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testAddRushingThenReceiving() throws Exception {
		String key = "00-0000069";

		RawStats rushingInput = new RawStats(
				"M.Brown",
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
		);
		builder.addRushing(key, rushingInput);

		RawStats receivingInput = new RawStats(
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
		);
		builder.addReceiving(key, receivingInput);

		PlayerStats playerStats = PlayerStats.builder("M.Brown", key)
				.rushingAttempts(12)
				.rushingYards(36)
				.rushingTouchdowns(1)
				.rushingLong(12)
				.rushingLongTouchdown(1)
				.receptions(4)
				.receivingYards(65)
				.receivingTouchdowns(1)
				.receivingLong(23)
				.receivingLongTouchdown(13)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testTranslateFumbleStats() throws Exception {
		RawStats input = new RawStats(
				"J.Flacco",
				null,
				null,
				null,
				2,
				1,
				0,
				0,
				12,
				null,
				null,
				null,
				null,
				null,
				null
		);

		String key = "00-0030400";

		PlayerStats expected = PlayerStats.builder("J.Flacco", key)
				.fumbles(2)
				.fumblesLost(0)
				.fumblesRecovered(1)
				.fumbleYards(12)
				.build();

		PlayerStats.Builder actual = builder.translateFumbleStats(key, input);

		assertEquals(actual.build(), expected);
	}

	@Test
	public void testAddFumbles() throws Exception {
		RawStats input = new RawStats(
				"J.Flacco",
				null,
				null,
				null,
				2,
				1,
				0,
				0,
				12,
				null,
				null,
				null,
				null,
				null,
				null
		);

		String key = "00-0030400";

		PlayerStats playerStats = PlayerStats.builder("J.Flacco", key)
				.fumbles(2)
				.fumblesLost(0)
				.fumblesRecovered(1)
				.fumbleYards(12)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		builder.addFumbles(key, input);

		assertEquals(builder.build(), expected);
	}

	@Test
	public void testAddRushingThenFumbles() throws Exception {
		String key = "00-0000069";

		RawStats rushingInput = new RawStats(
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
		);
		builder.addRushing(key, rushingInput);

		RawStats receivingInput = new RawStats(
				"R.Rice",
				null,
				null,
				null,
				2,
				1,
				0,
				0,
				12,
				null,
				null,
				null,
				null,
				null,
				null
		);
		builder.addFumbles(key, receivingInput);

		PlayerStats playerStats = PlayerStats.builder("R.Rice", key)
				.rushingAttempts(12)
				.rushingYards(36)
				.rushingTouchdowns(1)
				.rushingLong(12)
				.rushingLongTouchdown(1)
				.fumbles(2)
				.fumblesLost(0)
				.fumblesRecovered(1)
				.fumbleYards(12)
				.build();

		GameStats expected = new GameStats(ImmutableMap.<String, PlayerStats>builder()
				.put(key, playerStats)
				.build()
		);

		assertEquals(builder.build(), expected);
	}
}