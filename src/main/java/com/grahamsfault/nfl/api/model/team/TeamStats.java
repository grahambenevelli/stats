package com.grahamsfault.nfl.api.model.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.api.model.player.PlayerStatsSet;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamStats {

	private final PlayerStatsSet passing;
	private final PlayerStatsSet rushing;
	private final PlayerStatsSet receiving;
	private final PlayerStatsSet fumbles;

	@JsonCreator
	public TeamStats(
			@JsonProperty("passing") PlayerStatsSet passing,
			@JsonProperty("rushing") PlayerStatsSet rushing,
			@JsonProperty("receiving") PlayerStatsSet receiving,
			@JsonProperty("fumbles") PlayerStatsSet fumbles
	) {
		this.passing = passing;
		this.rushing = rushing;
		this.receiving = receiving;
		this.fumbles = fumbles;
	}

	public PlayerStatsSet getPassing() {
		return passing;
	}

	public PlayerStatsSet getRushing() {
		return rushing;
	}

	public PlayerStatsSet getReceiving() {
		return receiving;
	}

	public PlayerStatsSet getFumbles() {
		return fumbles;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("passing", passing)
				.add("rushing", rushing)
				.add("receiving", receiving)
				.add("fumbles", fumbles)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TeamStats)) return false;
		TeamStats teamStats = (TeamStats) o;
		return Objects.equals(getPassing(), teamStats.getPassing()) &&
				Objects.equals(getRushing(), teamStats.getRushing()) &&
				Objects.equals(getReceiving(), teamStats.getReceiving()) &&
				Objects.equals(getFumbles(), teamStats.getFumbles());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPassing(),
				getRushing(),
				getReceiving(),
				getFumbles()
		);
	}
}
