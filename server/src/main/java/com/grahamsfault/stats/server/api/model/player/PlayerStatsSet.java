package com.grahamsfault.stats.server.api.model.player;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerStatsSet {

	private Map<String, RawStats> stats = new HashMap<>();

	@JsonAnySetter
	public PlayerStatsSet setDynamicProperty(String playerId, RawStats rawStats) {
		rawStats.setPlayerId(playerId);
		this.stats.put(playerId, rawStats);
		return this;
	}

	public Map<String, RawStats> getStats() {
		return stats;
	}

	public PlayerStatsSet setStats(Map<String, RawStats> stats) {
		this.stats = stats;
		return this;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("stats", stats)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerStatsSet)) return false;
		PlayerStatsSet that = (PlayerStatsSet) o;
		return Objects.equals(getStats(), that.getStats());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStats());
	}
}
