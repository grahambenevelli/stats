package com.grahamsfault.nfl.stats.manager;

import com.grahamsfault.nfl.stats.AbstractMysqlIntegrationTest;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.api.model.Team;
import com.grahamsfault.nfl.stats.api.model.player.Position;
import com.grahamsfault.nfl.stats.dao.PlayerDAO;
import com.grahamsfault.nfl.stats.dao.mysql.MySQLPlayerDAO;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PlayerManagerIntegrationTest extends AbstractMysqlIntegrationTest {

	private PlayerManager playerManager;

	@BeforeClass
	public void setup() {
		DataSource dataSource = generateDataSource(
				"192.168.10.11",
				3306,
				"stats",
				"homestead",
				"secret"
		);
		PlayerDAO playerDAO = new MySQLPlayerDAO(dataSource);
		playerManager = new PlayerManager(playerDAO);
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
								81,
								"ACT",
								Team.TENNESSEE,
								Position.WR,
								null,
								14,
								null
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
								13,
								"ACT",
								Team.HOUSTON,
								Position.WR,
								null,
								0,
								null
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
								0,
								null,
								null,
								null,
								null,
								1,
								null
						))
				},
		};
	}

	@Test(dataProvider = "searchPlayerDataProvider")
	public void testSearchPlayer(String firstName, String lastName, Optional<Player> expected) throws Exception {
		Set<Player> players = playerManager.searchForPlayer(Optional.ofNullable(firstName), Optional.ofNullable(lastName));

		Optional<Player> first = players.stream().findFirst();

		if (expected.isPresent()) {
			assertTrue(first.isPresent());
			assertEquals(first.get(), expected.get());
		} else {
			assertTrue(!first.isPresent());
		}
	}
}