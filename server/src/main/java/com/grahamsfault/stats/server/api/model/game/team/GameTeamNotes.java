package com.grahamsfault.stats.server.api.model.game.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.stats.server.api.model.Team;
import com.grahamsfault.stats.server.api.model.team.TeamStats;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameTeamNotes {

	private final QuarterlyScores quarterlyScores;
	private final Team team;
	private final TeamStats teamStats;

	@JsonCreator
	public GameTeamNotes(
			@JsonProperty("score") QuarterlyScores quarterlyScores,
			@JsonProperty("abbr") Team team,
			@JsonProperty("stats") TeamStats teamStats
	) {
		this.quarterlyScores = quarterlyScores;
		this.team = team;
		this.teamStats = teamStats;
	}

	public QuarterlyScores getQuarterlyScores() {
		return quarterlyScores;
	}

	public Team getTeam() {
		return team;
	}

	public TeamStats getTeamStats() {
		return teamStats;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("quarterlyScores", quarterlyScores)
				.add("team", team)
				.add("teamStats", teamStats)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameTeamNotes)) return false;
		GameTeamNotes that = (GameTeamNotes) o;
		return Objects.equals(getQuarterlyScores(), that.getQuarterlyScores()) &&
				getTeam() == that.getTeam()
				&& Objects.equals(getTeamStats(), that.getTeamStats())
				;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getQuarterlyScores(), getTeam()
				, getTeamStats()
		);
	}
}
