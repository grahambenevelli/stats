package com.grahamsfault.nfl.manager.helper.average;

import com.google.common.collect.ImmutableSet;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.model.AverageStats;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.model.PlayerStats;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class NaiveAverageHelperTest {

	private Player jamaalCharles;
	private PlayerStats jamaalCharlesStats;

	private Player priestHolmes;
	private PlayerStats priestHolmesStats;

	private Player rickyWilliams;
	private PlayerStats rickyWilliamsStats;

	private Player selvinYoung;
	// private PlayerStats selvinYoungStats; Doesn't have one

	private Player malcolmBrown;
	private PlayerStats malcolmBrownStats;

	private Player vinceYoung;
	private PlayerStats vinceYoungStats;

	private Player coltMcCoy;
	private PlayerStats coltMcCoyStats;

	@BeforeClass
	public void setup() throws MalformedURLException {
		jamaalCharles = new Player(
				"1/1/1900",
				"Texas",
				"Jamaal",
				"Charles",
				"Jamaal Charles",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.RB
		);

		jamaalCharlesStats = PlayerStats.builder("Jamaal Charles", "fake id")
				.rushingAttempts(50)
				.rushingTouchdowns(1)
				.build();

		priestHolmes = new Player(
				"1/1/1900",
				"Texas",
				"Priest",
				"Holmes",
				"Priest Holmes",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.RB
		);

		priestHolmesStats = PlayerStats.builder("Priest Holmes", "fake id")
				.rushingAttempts(75)
				.rushingTouchdowns(1)
				.build();

		rickyWilliams = new Player(
				"1/1/1900",
				"Texas",
				"Ricky",
				"Williams",
				"Ricky Williams",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.RB
		);

		rickyWilliamsStats = PlayerStats.builder("Ricky Williams", "fake id")
				.rushingAttempts(100)
				.rushingTouchdowns(1)
				.build();

		selvinYoung = new Player(
				"1/1/1900",
				"Texas",
				"Selvin",
				"Young",
				"Selvin Young",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.RB
		);

		malcolmBrown = new Player(
				"1/1/1900",
				"Texas",
				"Malcolm",
				"Brown",
				"Malcolm Brown",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.RB
		);

		malcolmBrownStats = PlayerStats.builder("Malcolm Brown", "fake id")
				.rushingAttempts(175)
				.rushingTouchdowns(1)
				.build();

		vinceYoung = new Player(
				"1/1/1900",
				"Texas",
				"Vince",
				"Young",
				"Vince Young",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.QB
		);

		vinceYoungStats = PlayerStats.builder("Vince Young", "fake id")
				.passingAttempts(500)
				.passingCompletions(300)
				.rushingAttempts(60)
				.rushingTouchdowns(4)
				.build();

		coltMcCoy = new Player(
				"1/1/1900",
				"Texas",
				"Colt",
				"McCoy",
				"Colt McCoy",
				"fake id",
				"fake name",
				42l,
				new URL("https://fake.url"),
				65,
				210,
				3,
				21,
				"ACT",
				Team.DENVER,
				Position.QB
		);

		coltMcCoyStats = PlayerStats.builder("Colt McCoy", "fake id")
				.passingAttempts(500)
				.passingCompletions(350)
				.rushingAttempts(10)
				.rushingTouchdowns(0)
				.build();
	}

	@Test
	public void testGetAveragePerPosition() throws Exception {
		PlayerManager playerManagerMock = Mockito.mock(PlayerManager.class);
		StatsManager statsManagerMock = Mockito.mock(StatsManager.class);
		mockManagers(playerManagerMock, statsManagerMock);

		NaiveAverageHelper naiveAverageHelper = new NaiveAverageHelper(playerManagerMock, statsManagerMock);
		Map<Position, AverageStats> averagePerPosition = naiveAverageHelper.getAveragePerPosition(2009);

		AverageStats rbAverages = averagePerPosition.get(Position.RB);
		Assert.assertEquals(rbAverages.getRushingTouchdowns(), 1.0);
		Assert.assertEquals(rbAverages.getRushingAttempts(), 100.0);

		AverageStats qbAverages = averagePerPosition.get(Position.QB);
		Assert.assertEquals(qbAverages.getPassingAttempts(), 500.0);
		Assert.assertEquals(qbAverages.getPassingCompletions(), 325.0);
		Assert.assertEquals(qbAverages.getRushingTouchdowns(), 2.0);
		Assert.assertEquals(qbAverages.getRushingAttempts(), 35.0);
	}

	private void mockManagers(PlayerManager playerManagerMock, StatsManager statsManagerMock) {
		ImmutableSet<Player> players = ImmutableSet.<Player>builder()
				.add(jamaalCharles)
				.add(priestHolmes)
				.add(rickyWilliams)
				.add(selvinYoung)
				.add(malcolmBrown)
				.add(vinceYoung)
				.add(coltMcCoy)
				.build();

		Mockito.when(playerManagerMock.getPlayersPerYear(2009))
				.thenReturn(players);

		Mockito.when(statsManagerMock.getPlayerYearlyStats(jamaalCharles, 2009))
				.thenReturn(Optional.of(jamaalCharlesStats));

		Mockito.when(statsManagerMock.getPlayerYearlyStats(priestHolmes, 2009))
				.thenReturn(Optional.of(priestHolmesStats));

		Mockito.when(statsManagerMock.getPlayerYearlyStats(rickyWilliams, 2009))
				.thenReturn(Optional.of(rickyWilliamsStats));

		Mockito.when(statsManagerMock.getPlayerYearlyStats(malcolmBrown, 2009))
				.thenReturn(Optional.of(malcolmBrownStats));

		Mockito.when(statsManagerMock.getPlayerYearlyStats(selvinYoung, 2009))
				.thenReturn(Optional.empty());

		Mockito.when(statsManagerMock.getPlayerYearlyStats(vinceYoung, 2009))
				.thenReturn(Optional.of(vinceYoungStats));

		Mockito.when(statsManagerMock.getPlayerYearlyStats(coltMcCoy, 2009))
				.thenReturn(Optional.of(coltMcCoyStats));
	}
}