package com.grahamsfault.stats.server.api.model.game.drive.play;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayWrapper {

	private final List<PlayEntry> plays = Lists.newArrayList();

	@JsonAnySetter
	public void setDynamicProperty(String name, PlayEntry play) {
		plays.add(play);
	}

	public List<PlayEntry> getPlays() {
		return plays;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("plays", plays)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayWrapper)) return false;
		PlayWrapper that = (PlayWrapper) o;
		return Objects.equals(plays, that.plays);
	}

	@Override
	public int hashCode() {
		return Objects.hash(plays);
	}
}
