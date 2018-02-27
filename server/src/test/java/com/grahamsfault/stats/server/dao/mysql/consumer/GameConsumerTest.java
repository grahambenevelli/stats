package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class GameConsumerTest {

	@BeforeClass
	public void setup() throws MalformedURLException, SQLException {

	}

	@DataProvider(name = "games")
	public static Object[][] games() {
		return new Object[][]{
				{
						GameConsumer.consumer(),
						new TestReadOnlyResultSet()
								.value("away", Team.ARIZONA.abbreviation)
								.value("home", Team.ATLANTA.abbreviation)
								.value("season_type", GameType.REG.type)
								.value("day", 5)
								.value("eid", "fake-eid")
								.value("gamekey", "fake-game-key")
								.value("meridiem", "fake-meridiem")
								.value("time", "fake-time")
								.value("month", 4)
								.value("wday", "SAT")
								.value("week", 4)
								.value("year", 2014),
						new Game(
								Team.ARIZONA,
								Team.ATLANTA,
								5,
								"fake-eid",
								"fake-game-key",
								"fake-meridiem",
								4,
								GameType.REG,
								"fake-time",
								"SAT",
								4,
								2014
						),
				},
				{
						GameConsumer.consumer()
								.setYear("year_bak"),
						new TestReadOnlyResultSet()
								.value("away", Team.ARIZONA.abbreviation)
								.value("home", Team.ATLANTA.abbreviation)
								.value("season_type", GameType.REG.type)
								.value("day", 5)
								.value("eid", "fake-eid")
								.value("gamekey", "fake-game-key")
								.value("meridiem", "fake-meridiem")
								.value("time", "fake-time")
								.value("month", 4)
								.value("wday", "SAT")
								.value("week", 4)
								.value("year_bak", 2014),
						new Game(
								Team.ARIZONA,
								Team.ATLANTA,
								5,
								"fake-eid",
								"fake-game-key",
								"fake-meridiem",
								4,
								GameType.REG,
								"fake-time",
								"SAT",
								4,
								2014
						),
				},
				{
						GameConsumer.consumer()
								.setMonth("month_bak"),
						new TestReadOnlyResultSet()
								.value("away", Team.ARIZONA.abbreviation)
								.value("home", Team.ATLANTA.abbreviation)
								.value("season_type", GameType.REG.type)
								.value("day", 5)
								.value("eid", "fake-eid")
								.value("gamekey", "fake-game-key")
								.value("meridiem", "fake-meridiem")
								.value("time", "fake-time")
								.value("month_bak", 4)
								.value("wday", "SAT")
								.value("week", 4)
								.value("year", 2014),
						new Game(
								Team.ARIZONA,
								Team.ATLANTA,
								5,
								"fake-eid",
								"fake-game-key",
								"fake-meridiem",
								4,
								GameType.REG,
								"fake-time",
								"SAT",
								4,
								2014
						),
				}
		};

	}

	@Test(dataProvider = "games")
	public void testConsume(GameConsumer consumer, TestReadOnlyResultSet resultSet, Game expected) throws SQLException {
		Game actual = consumer.consume(resultSet);
		assertEquals(actual, expected);
	}
}