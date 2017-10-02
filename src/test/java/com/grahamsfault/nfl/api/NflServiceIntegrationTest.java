package com.grahamsfault.nfl.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameNotes;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.api.model.game.drive.Drive;
import com.grahamsfault.nfl.api.model.game.drive.GameDrives;
import com.grahamsfault.nfl.api.model.game.drive.play.PlayEntry;
import com.grahamsfault.nfl.api.model.game.drive.play.PlayPlayerStatEntry;
import com.grahamsfault.nfl.api.model.game.drive.play.PlayPlayersWrapper;
import com.grahamsfault.nfl.api.model.game.team.GameTeamNotes;
import com.grahamsfault.nfl.api.model.game.team.QuarterlyScores;
import com.grahamsfault.nfl.api.model.player.PlayerStatsSet;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.nfl.api.model.team.TeamStats;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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