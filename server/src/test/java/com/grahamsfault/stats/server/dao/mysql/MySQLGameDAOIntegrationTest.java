package com.grahamsfault.stats.server.dao.mysql;

import com.grahamsfault.stats.server.AbstractMysqlIntegrationTest;
import com.grahamsfault.stats.server.api.model.Game;
import com.grahamsfault.stats.server.api.model.Team;
import com.grahamsfault.stats.server.api.model.game.GameType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class MySQLGameDAOIntegrationTest extends AbstractMysqlIntegrationTest {

	private MySQLGameDAO gameDAO;
	private Game game;

	@BeforeClass
	public void setup() throws MalformedURLException, SQLException {
		DataSource dataSource = generateDataSource(
				"192.168.10.11",
				3306,
				"stats",
				"homestead",
				"secret"
		);

		gameDAO = new MySQLGameDAO(dataSource);

		game = new Game(
				Team.ARIZONA,
				Team.BALTIMORE,
				24,
				"fake eid",
				"fake game key",
				"PM",
				13,
				GameType.PRE,
				"26:00",
				"sun",
				8,
				1004
		);

		gameDAO.updateGame(game);
	}

	@AfterClass
	public void tearDown() throws SQLException {
		gameDAO.deleteGame("fake eid");
	}

	@Test
	public void testGetPlayerById() throws MalformedURLException, SQLException {
		Optional<Game> result = gameDAO.getByEid("fake eid");

		assertTrue(result.isPresent());
		assertEquals(result.get(), game);
	}
}