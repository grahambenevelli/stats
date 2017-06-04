package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.AbstractMysqlIntegrationTest;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MySQLPlayerDAOIntegrationTest extends AbstractMysqlIntegrationTest {

	private PlayerDAO playerDAO;

	@BeforeClass
	public void setup() {
		DataSource dataSource = generateDataSource(
				"192.168.10.11",
				3306,
				"stats",
				"homestead",
				"secret"
		);

		playerDAO = new MySQLPlayerDAO(dataSource);
	}

	@AfterClass
	public void tearDown() throws SQLException {
		DataSource dataSource = generateDataSource(
				"192.168.10.11",
				3306,
				"stats",
				"homestead",
				"secret"
		);

		String sql = "TRUNCATE TABLE players;";

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.execute();
		}
	}

	@Test
	public void testGetPlayerById() throws MalformedURLException, SQLException {
		Player player = Player.builder("00-0024218", "Vince", "Young")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Vince Young")
				.setGsisName("V.Young")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setYearsPro(7)
				.setStatus("INACTIVE")
				.setTeam(Team.TENNESSEE)
				.build();

		assertTrue(playerDAO.isEmpty());
		playerDAO.updatePlayer(player);
		Optional<Player> result = playerDAO.getById("00-0024218");

		assertFalse(playerDAO.isEmpty());
		assertTrue(result.isPresent());
		assertEquals(result.get(), player);

		player = Player.builder("00-0024218", "Vince", "Young")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Vince Young")
				.setGsisName("V.Young")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setYearsPro(7)
				.setStatus("INACTIVE")
				.setTeam(Team.FREE_AGENT)
				.build();

		playerDAO.updatePlayer(player);
		result = playerDAO.getById("00-0024218");

		assertFalse(playerDAO.isEmpty());
		assertTrue(result.isPresent());
		assertEquals(result.get(), player);
	}

	@Test
	public void testSearch() throws MalformedURLException, SQLException {
		Player player = Player.builder("00-0024218", "Vince", "Young")
				.setBirthdate("5/18/1983")
				.setCollege("Texas")
				.setFullName("Vince Young")
				.setGsisName("V.Young")
				.setHeight(77)
				.setNumber(0)
				.setProfileId(2506875L)
				.setProfileUrl(new URL("http://www.nfl.com/player/vinceyoung/2506875/profile"))
				.setWeight(232)
				.setYearsPro(7)
				.setStatus("INACTIVE")
				.setTeam(Team.TENNESSEE)
				.build();

		assertTrue(false);

		playerDAO.updatePlayer(player);

		Set<Player> players = playerDAO.searchForPlayer(Optional.of("Vince"), Optional.of("Young"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.of("Vince"), Optional.empty());

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.empty(), Optional.of("Young"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.empty(), Optional.of("You"));

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);

		players = playerDAO.searchForPlayer(Optional.of("ince"), Optional.empty());

		assertFalse(players.isEmpty());
		assertEquals(players.stream().findFirst().get(), player);
	}
}