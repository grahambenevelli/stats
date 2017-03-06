package com.grahamsfault.nfl.api.model.game.drive.play;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.api.model.Team;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayPlayerStatEntry {

	private final Integer sequence;
	private final Team clubcode;
	private final String playerName;
	private final Integer statId;
	private final Integer yards;

	@JsonCreator
	public PlayPlayerStatEntry(
			@JsonProperty("sequence") Integer sequence,
			@JsonProperty("clubcode") Team clubcode,
			@JsonProperty("playerName") String playerName,
			@JsonProperty("statId") Integer statId,
			@JsonProperty("yards") Integer yards
	) {
		this.sequence = sequence;
		this.clubcode = clubcode;
		this.playerName = playerName;
		this.statId = statId;
		this.yards = yards;
	}

	public Integer getSequence() {
		return sequence;
	}

	public Team getClubcode() {
		return clubcode;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Integer getStatId() {
		return statId;
	}

	public Integer getYards() {
		return yards;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("sequence", sequence)
				.add("clubcode", clubcode)
				.add("playerName", playerName)
				.add("statId", statId)
				.add("yards", yards)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayPlayerStatEntry)) return false;
		PlayPlayerStatEntry that = (PlayPlayerStatEntry) o;
		return Objects.equals(getSequence(), that.getSequence()) &&
				Objects.equals(getClubcode(), that.getClubcode()) &&
				Objects.equals(getPlayerName(), that.getPlayerName()) &&
				Objects.equals(getStatId(), that.getStatId()) &&
				Objects.equals(getYards(), that.getYards());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSequence(), getClubcode(), getPlayerName(), getStatId(), getYards());
	}
}
