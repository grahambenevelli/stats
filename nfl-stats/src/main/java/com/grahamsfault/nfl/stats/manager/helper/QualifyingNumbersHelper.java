package com.grahamsfault.nfl.stats.manager.helper;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.grahamsfault.nfl.stats.api.model.player.Position;
import com.grahamsfault.nfl.stats.model.PlayerStats;

import java.util.Map;
import java.util.Optional;

/**
 * Helper to determine if a player has qualifying statistics
 */
public class QualifyingNumbersHelper {

	public static final int DEFAULT_QB_QUALIFYING_PASSING_ATTEMPTS = 100;
	public static final int DEFAULT_RB_QUALIFYING_RUSHING_ATTEMPTS = 50;
	public static final int DEFAULT_RB_QUALIFYING_RECEPTIONS = 20;
	public static final int DEFAULT_WR_QUALIFYING_RECEPTIONS = 30;
	public static final int DEFAULT_TE_QUALIFYING_RECEPTIONS = 20;

	private final int qbQualifyingPassingAttempts;
	private final int rbQualifyingRushingAttempts;
	private final int wrQualifyingReceptions;
	private final int teQualifyingReceptions;

	public QualifyingNumbersHelper() {
		this(
				DEFAULT_QB_QUALIFYING_PASSING_ATTEMPTS,
				DEFAULT_RB_QUALIFYING_RUSHING_ATTEMPTS,
				DEFAULT_WR_QUALIFYING_RECEPTIONS,
				DEFAULT_TE_QUALIFYING_RECEPTIONS
		);
	}

	public QualifyingNumbersHelper(
			int qbQualifyingPassingAttempts,
			int rbQualifyingRushingAttempts,
			int wrQualifyingReceptions,
			int teQualifyingReceptions
	) {
		this.qbQualifyingPassingAttempts = qbQualifyingPassingAttempts;
		this.rbQualifyingRushingAttempts = rbQualifyingRushingAttempts;
		this.wrQualifyingReceptions = wrQualifyingReceptions;
		this.teQualifyingReceptions = teQualifyingReceptions;
	}

	/**
	 * Determines if the given QB stats qualify
	 *
	 * @param playerStats The player stats
	 * @return true if qualifies, false if not
	 */
	public boolean qbQualifies(PlayerStats playerStats) {
		return playerStats.getPassingAttempts() > qbQualifyingPassingAttempts;
	}

	/**
	 * Determines if the given RB stats qualify
	 *
	 * @param playerStats The player stats
	 * @return true if qualifies, false if not
	 */
	public boolean rbQualifies(PlayerStats playerStats) {
		return playerStats.getRushingAttempts() > rbQualifyingRushingAttempts || playerStats.getReceptions() > DEFAULT_RB_QUALIFYING_RECEPTIONS;
	}

	/**
	 * Determines if the given WR stats qualify
	 *
	 * @param playerStats The player stats
	 * @return true if qualifies, false if not
	 */
	public boolean wrQualifies(PlayerStats playerStats) {
		return playerStats.getReceptions() > wrQualifyingReceptions;
	}

	/**
	 * Determines if the given TE stats qualify
	 *
	 * @param playerStats The player stats
	 * @return true if qualifies, false if not
	 */
	public boolean teQualifies(PlayerStats playerStats) {
		return playerStats.getReceptions() > teQualifyingReceptions;
	}

	/**
	 * Determines if the given stats qualify for the given position
	 *
	 * @param position The position of the player
	 * @param playerYearlyStats The player stats
	 * @return true if qualifies, false if not
	 */
	public boolean qualifies(Position position, Optional<PlayerStats> playerYearlyStats) {
		if (!playerYearlyStats.isPresent()) {
			return false;
		}

		Map<Position, Supplier<Boolean>> map = ImmutableMap.<Position, Supplier<Boolean>>builder()
				.put(Position.QB, () -> qbQualifies(playerYearlyStats.get()))
				.put(Position.RB, () -> rbQualifies(playerYearlyStats.get()))
				.put(Position.WR, () -> wrQualifies(playerYearlyStats.get()))
				.put(Position.TE, () -> teQualifies(playerYearlyStats.get()))
				.build();

		return map.get(position).get();
	}
}
