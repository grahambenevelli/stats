package com.grahamsfault.nfl.model;

import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Objects;

public class YearlyStats {

	private final Map<String, PlayerStats> playerStatsMap;

	public YearlyStats(Map<String, PlayerStats> playerStatsMap) {
		this.playerStatsMap = playerStatsMap;
	}

	public Map<String, PlayerStats> getPlayerStatsMap() {
		return playerStatsMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof YearlyStats)) return false;
		YearlyStats gameStats = (YearlyStats) o;
		return Objects.equals(getPlayerStatsMap(), gameStats.getPlayerStatsMap());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayerStatsMap());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("playerStatsMap", playerStatsMap)
				.toString();
	}
}
