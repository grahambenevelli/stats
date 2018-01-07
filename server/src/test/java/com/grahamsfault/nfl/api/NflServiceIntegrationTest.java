package com.grahamsfault.nfl.api;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameNotes;
import com.grahamsfault.nfl.api.model.player.Position;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.ClientBuilder;

import static org.testng.Assert.assertEquals;

public class NflServiceIntegrationTest {

	private NflService nflService;

	@BeforeMethod
	public void setup() {
		nflService = new NflService(ClientBuilder.newClient());
	}

	@Test
	public void testPlayerBasicInfoAmendola() throws Exception {
		Player player = nflService.getPlayerBasicInfo("00-0026035");

		assertEquals(player.getFirstName(), "Danny");
		assertEquals(player.getLastName(), "Amendola");
		assertEquals(player.getNumber(), new Integer(80));
		assertEquals(player.getPosition(), Position.WR);
		assertEquals(player.getHeight(), new Integer(71));
		assertEquals(player.getWeight(), new Integer(190));
		assertEquals(player.getBirthdate(), "11/2/1985");
		assertEquals(player.getCollege(), "Texas Tech");
		assertEquals(player.getExperience(), new Integer(9));
		assertEquals(player.getHighSchool(), "The Woodlands HS [TX]");
	}

	@Test
	public void testPlayerBasicInfoFlozell() throws Exception {
		Player player = nflService.getPlayerBasicInfo("00-0000045");

		assertEquals(player.getFirstName(), "Flozell");
		assertEquals(player.getLastName(), "Adams");
		assertEquals(player.getNumber(), null);
		assertEquals(player.getPosition(), null);
		assertEquals(player.getHeight(), new Integer(79));
		assertEquals(player.getWeight(), new Integer(338));
		assertEquals(player.getBirthdate(), "5/18/1975");
		assertEquals(player.getCollege(), "Michigan State");
		assertEquals(player.getExperience(), new Integer(13));
		assertEquals(player.getHighSchool(), null);
	}

	@Test
	public void testGameNotes() throws Exception {
		GameNotes notes = nflService.getGameNotes("2011100910");

		assertEquals(notes.getHome().getTeam(), Team.DENVER);

		assertEquals(notes.getAway().getTeam(), Team.LOS_ANGELES_CHARGERS);
	}
}