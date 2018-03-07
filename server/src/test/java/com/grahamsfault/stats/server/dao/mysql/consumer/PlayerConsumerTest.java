package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.player.Position;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class PlayerConsumerTest {

	@DataProvider(name = "players")
	public static Object[][] players() throws MalformedURLException {
		return new Object[][]{
				{
						PlayerConsumer.consumer(),
						new TestReadOnlyResultSet()
								.value("birthdate", "10/4/1983")
								.value("college", "Texas")
								.value("first_name", "Graham")
								.value("last_name", "Benevelli")
								.value("full_name", "Graham Benevelli")
								.value("gsis_id", "gsisId")
								.value("gsis_name", "gsisName")
								.value("profile_id", 8964L)
								.value("profile_url", new URL("https://fake.url"))
								.value("height", 59)
								.value("weight", 214)
								.value("number", 1).value("status", "ACTIVE")
								.value("team", Team.HOUSTON.abbreviation)
								.value("position", Position.QB.abbreviation)
								.value("experience", 6),
						Player.builder("gsisId", "Graham", "Benevelli")
								.setBirthdate("10/4/1983")
								.setCollege("Texas")
								.setFullName("Graham Benevelli")
								.setGsisName("gsisName")
								.setProfileId(8964L)
								.setProfileUrl(new URL("https://fake.url"))
								.setHeight(59)
								.setWeight(214)
								.setNumber(1)
								.setStatus("ACTIVE")
								.setTeam(Team.HOUSTON)
								.setPosition(Position.QB)
								.setExperience(6)
								.build(),
				},
//				{
//						PlayerConsumer.consumer()
//								.setBirthdate("birthdate_bak"),
//						new TestReadOnlyResultSet()
//								.value("birthdate_bak", "10/4/1983")
//								.value("college", "Texas")
//								.value("first_name", "Graham")
//								.value("last_name", "Benevelli")
//								.value("full_name", "Graham Benevelli")
//								.value("gsis_id", "gsisId")
//								.value("gsis_name", "gsisName")
//								.value("profile_id", 8964L)
//								.value("profile_url", new URL("https://fake.url"))
//								.value("height", 59)
//								.value("weight", 214)
//								.value("number", 1).value("status", "ACTIVE")
//								.value("team", Team.HOUSTON.abbreviation)
//								.value("position", Position.QB.abbreviation)
//								.value("experience", 6),
//						Player.builder("gsisId", "Graham", "Benevelli")
//								.setBirthdate("10/4/1983")
//								.setCollege("Texas")
//								.setFullName("Graham Benevelli")
//								.setGsisName("gsisName")
//								.setProfileId(8964L)
//								.setProfileUrl(new URL("https://fake.url"))
//								.setHeight(59)
//								.setWeight(214)
//								.setNumber(1)
//								.setStatus("ACTIVE")
//								.setTeam(Team.HOUSTON)
//								.setPosition(Position.QB)
//								.setExperience(6)
//								.build(),
//				},
//				{
//						PlayerConsumer.consumer()
//								.setBirthdate("profile_id_bak"),
//						new TestReadOnlyResultSet()
//								.value("birthdate", "10/4/1983")
//								.value("college", "Texas")
//								.value("first_name", "Graham")
//								.value("last_name", "Benevelli")
//								.value("full_name", "Graham Benevelli")
//								.value("gsis_id", "gsisId")
//								.value("gsis_name", "gsisName")
//								.value("profile_id_bak", 8964L)
//								.value("profile_url", new URL("https://fake.url"))
//								.value("height", 59)
//								.value("weight", 214)
//								.value("number", 1).value("status", "ACTIVE")
//								.value("team", Team.HOUSTON.abbreviation)
//								.value("position", Position.QB.abbreviation)
//								.value("experience", 6),
//						Player.builder("gsisId", "Graham", "Benevelli")
//								.setBirthdate("10/4/1983")
//								.setCollege("Texas")
//								.setFullName("Graham Benevelli")
//								.setGsisName("gsisName")
//								.setProfileId(8964L)
//								.setProfileUrl(new URL("https://fake.url"))
//								.setHeight(59)
//								.setWeight(214)
//								.setNumber(1)
//								.setStatus("ACTIVE")
//								.setTeam(Team.HOUSTON)
//								.setPosition(Position.QB)
//								.setExperience(6)
//								.build(),
//				},
//				{
//						PlayerConsumer.consumer()
//								.setBirthdate("profile_url_bak"),
//						new TestReadOnlyResultSet()
//								.value("birthdate", "10/4/1983")
//								.value("college", "Texas")
//								.value("first_name", "Graham")
//								.value("last_name", "Benevelli")
//								.value("full_name", "Graham Benevelli")
//								.value("gsis_id", "gsisId")
//								.value("gsis_name", "gsisName")
//								.value("profile_id", 8964L)
//								.value("profile_url_bak", new URL("https://fake.url"))
//								.value("height", 59)
//								.value("weight", 214)
//								.value("number", 1).value("status", "ACTIVE")
//								.value("team", Team.HOUSTON.abbreviation)
//								.value("position", Position.QB.abbreviation)
//								.value("experience", 6),
//						Player.builder("gsisId", "Graham", "Benevelli")
//								.setBirthdate("10/4/1983")
//								.setCollege("Texas")
//								.setFullName("Graham Benevelli")
//								.setGsisName("gsisName")
//								.setProfileId(8964L)
//								.setProfileUrl(new URL("https://fake.url"))
//								.setHeight(59)
//								.setWeight(214)
//								.setNumber(1)
//								.setStatus("ACTIVE")
//								.setTeam(Team.HOUSTON)
//								.setPosition(Position.QB)
//								.setExperience(6)
//								.build(),
//				},
//				{
//						PlayerConsumer.consumer()
//								.setBirthdate("height_bak"),
//						new TestReadOnlyResultSet()
//								.value("birthdate", "10/4/1983")
//								.value("college", "Texas")
//								.value("first_name", "Graham")
//								.value("last_name", "Benevelli")
//								.value("full_name", "Graham Benevelli")
//								.value("gsis_id", "gsisId")
//								.value("gsis_name", "gsisName")
//								.value("profile_id", 8964L)
//								.value("profile_url", new URL("https://fake.url"))
//								.value("height_bak", 59)
//								.value("weight", 214)
//								.value("number", 1).value("status", "ACTIVE")
//								.value("team", Team.HOUSTON.abbreviation)
//								.value("position", Position.QB.abbreviation)
//								.value("experience", 6),
//						Player.builder("gsisId", "Graham", "Benevelli")
//								.setBirthdate("10/4/1983")
//								.setCollege("Texas")
//								.setFullName("Graham Benevelli")
//								.setGsisName("gsisName")
//								.setProfileId(8964L)
//								.setProfileUrl(new URL("https://fake.url"))
//								.setHeight(59)
//								.setWeight(214)
//								.setNumber(1)
//								.setStatus("ACTIVE")
//								.setTeam(Team.HOUSTON)
//								.setPosition(Position.QB)
//								.setExperience(6)
//								.build(),
//				},
		};

	}

	@Test(dataProvider = "players")
	public void testConsume(PlayerConsumer consumer, TestReadOnlyResultSet resultSet, Player expected) throws SQLException {
		Player actual = consumer.consume(resultSet);
		assertEquals(actual, expected);
	}
}