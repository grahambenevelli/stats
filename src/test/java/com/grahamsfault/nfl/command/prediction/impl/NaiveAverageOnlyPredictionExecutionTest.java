package com.grahamsfault.nfl.command.prediction.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.nfl.command.prediction.PredictionResults;
import com.grahamsfault.nfl.dao.ImportDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.dao.StatsDAO;
import com.grahamsfault.nfl.manager.ImportManager;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.manager.helper.average.NaiveAverageHelper;
import com.grahamsfault.nfl.model.PlayerStats;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class NaiveAverageOnlyPredictionExecutionTest {

	public static final Player TEST_PLAYER = Player.builder("gsisID", "firstName", "lastName")
			.setPosition(Position.QB)
			.build();

	@Test
	public void testRun() throws Exception {
		ImportDAO importDAO = mock(ImportDAO.class);
		when(importDAO.getYears()).thenReturn(ImmutableList.of(2015, 2016));

		PlayerDAO playerDAO = mock(PlayerDAO.class);
		when(playerDAO.getPlayersPerYear(2015)).thenReturn(ImmutableSet.<Player>builder()
				.add(TEST_PLAYER)
				.build()
		);

		StatsDAO statsDAO = mock(StatsDAO.class);
		when(statsDAO.getPlayerYearlyStats(TEST_PLAYER, 2015)).thenReturn(
				Optional.of(
						PlayerStats.builder("firstName lastName", "gsisID")
								.passingAttempts(100)
								.passingCompletions(50)
								.build()
				)
		);

		when(statsDAO.getPlayerYearlyStats(TEST_PLAYER, 2016)).thenReturn(
				Optional.of(
						PlayerStats.builder("firstName lastName", "gsisID")
								.passingAttempts(90)
								.passingCompletions(60)
								.build()
				)
		);

		ImportManager importManager = new ImportManager(
				importDAO
		);

		StatsManager statsManager = new StatsManager(
				statsDAO
		);

		PlayerManager playerManager = new PlayerManager(
				playerDAO
		);

		NaiveAverageHelper averageHelper = new NaiveAverageHelper(
				playerManager,
				statsManager
		);

		NaiveAverageOnlyPredictionExecution execution = new NaiveAverageOnlyPredictionExecution(
				importManager,
				statsManager,
				playerManager,
				averageHelper
		);

		PredictionResults results = execution.run();

		assertEquals(results.getQbStats().getPassingCompletions(), 10.0);
		assertEquals(results.getQbStats().getPassingAttempts(), 10.0);
	}
}