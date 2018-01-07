package com.grahamsfault.stats.server.dao.mysql;

import com.grahamsfault.stats.server.AbstractMysqlIntegrationTest;
import com.grahamsfault.stats.server.api.model.Player;
import com.grahamsfault.stats.server.api.model.Team;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MySQLPlayerDAOIntegrationTest extends AbstractMysqlIntegrationTest {

	private MySQLPlayerDAO playerDAO;
	private Player player;

	@BeforeClass
	public void setup() throws MalformedURLException, SQLException {
		DataSource dataSource = generateDataSource(
				"192.168.10.11",
				3306,
				"stats",
				"homestead",
				"secret"
		);

		playerDAO = new MySQLPlayerDAO(dataSource);

		player = Player.builder("hook'em", "Graham", "Benevelli")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Graham Benevelli")
				.setGsisName("G.Benevelli")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setExperience(7)
				.setStatus("INACTIVE")
				.setTeam(Team.TENNESSEE)
				.build();

		playerDAO.updatePlayer(player);
	}

	@AfterClass
	public void tearDown() throws SQLException {
		playerDAO.deletePlayer("hook'em");
	}

	@Test
	public void testGetPlayerById() throws MalformedURLException, SQLException {
		Optional<Player> result = playerDAO.getByGsisId("hook'em");

		assertFalse(playerDAO.isEmpty());
		assertTrue(result.isPresent());
		assertEquals(result.get(), player);

		player = Player.builder("hook'em", "Graham", "Benevelli")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Graham Benevelli")
				.setGsisName("G.Benevelli")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setExperience(7)
				.setStatus("INACTIVE")
				.setTeam(Team.FREE_AGENT)
				.build();;

		playerDAO.updatePlayer(player);
		result = playerDAO.getByGsisId("hook'em");

		assertFalse(playerDAO.isEmpty());
		assertTrue(result.isPresent());
		assertEquals(result.get(), player);
	}

	@Test
	public void testSearch() throws MalformedURLException, SQLException {
		Player player = Player.builder("hook'em", "Graham", "Benevelli")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Graham Benevelli")
				.setGsisName("G.Benevelli")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setExperience(7)
				.setStatus("INACTIVE")
				.setTeam(Team.TENNESSEE)
				.build();

		playerDAO.updatePlayer(player);

		Set<Player> players = playerDAO.searchForPlayer(Optional.of("Graham"), Optional.of("Benevelli"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.of("Graham"), Optional.empty());

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.empty(), Optional.of("Benevelli"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.empty(), Optional.of("Benev"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.of("raham"), Optional.empty());

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);
	}
}