package com.grahamsfault.stats.server.command.prediction.impl.helper;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.model.StdDevStats;
import com.grahamsfault.stats.server.manager.StatsManager;

/**
 * The STDDEV Stats Helper
 */
public class StdDevStatsHelper {

	private static StdDevStatsHelper instance;

	private final StdDevStats qbStdDevStats;
	private final StdDevStats rbStdDevStats;
	private final StdDevStats wrStdDevStats;
	private final StdDevStats teStdDevStats;

	private StdDevStatsHelper(StatsManager statsManager) {
		qbStdDevStats = statsManager.getStdDevForPosition(Position.QB);
		rbStdDevStats = statsManager.getStdDevForPosition(Position.RB);
		wrStdDevStats = statsManager.getStdDevForPosition(Position.WR);
		teStdDevStats = statsManager.getStdDevForPosition(Position.TE);
	}

	/**
	 * Get the StdDevStatsHelper instance
	 *
	 * @param statsManager The stats manager
	 * @return The single instance of StdDevStatsHelper
	 */
	public static StdDevStatsHelper instance(StatsManager statsManager) {
		if (instance == null) {
			instance = new StdDevStatsHelper(statsManager);
		}
		return instance;
	}

	/**
	 * Get the STDDEV stats for a given position
	 *
	 * @param position The position requesting
	 * @return The set of StdDevStats for a given position
	 */
	public StdDevStats getStdDevStats(Position position) {
		if (position == Position.QB) {
			return qbStdDevStats;
		}

		if (position == Position.WR) {
			return wrStdDevStats;
		}

		if (position == Position.RB) {
			return rbStdDevStats;
		}

		if (position == Position.TE) {
			return teStdDevStats;
		}

		throw new IllegalArgumentException("The position passed in to StdDevStatsHelper did not match a known position");
	}
}