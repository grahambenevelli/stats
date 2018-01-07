package com.grahamsfault.stats.server.api.model.game;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameStatsWrapper {
	@JsonProperty("nextupdate")
	public String nextupdate;

	@JsonProperty("profiles")
	private Map<String, GameNotes> profiles = new HashMap<>();

	@JsonAnySetter
	public void setDynamicProperty(String name, GameNotes map) {
		profiles.put(name, map);
	}

	public String getNextupdate() {
		return nextupdate;
	}

	public GameStatsWrapper setNextupdate(String nextupdate) {
		this.nextupdate = nextupdate;
		return this;
	}

	public Map<String, GameNotes> getProfiles() {
		return profiles;
	}

	public GameStatsWrapper setProfiles(Map<String, GameNotes> profiles) {
		this.profiles = profiles;
		return this;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("nextupdate", nextupdate)
				.add("profiles", profiles)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameStatsWrapper)) return false;
		GameStatsWrapper that = (GameStatsWrapper) o;
		return Objects.equals(nextupdate, that.nextupdate) &&
				Objects.equals(profiles, that.profiles);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nextupdate, profiles);
	}
}
