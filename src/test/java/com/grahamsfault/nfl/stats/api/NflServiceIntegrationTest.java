package com.grahamsfault.nfl.stats.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.api.model.player.Position;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NflServiceIntegrationTest {

	private NflService nflService;

	@BeforeMethod
	public void setup() {
		nflService = new NflService(new ObjectMapper());
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
}