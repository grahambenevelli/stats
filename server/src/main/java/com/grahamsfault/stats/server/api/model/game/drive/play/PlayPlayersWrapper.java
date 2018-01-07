package com.grahamsfault.stats.server.api.model.game.drive.play;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayPlayersWrapper {

	Map<String, List<PlayPlayerStatEntry>> playersMap = Maps.newHashMap();

	@JsonAnySetter
	public void setDynamicProperty(String name, List<PlayPlayerStatEntry> entries) {
		playersMap.put(name, entries);
	}

	public Map<String, List<PlayPlayerStatEntry>> getPlayersMap() {
		return playersMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayPlayersWrapper)) return false;
		PlayPlayersWrapper that = (PlayPlayersWrapper) o;
		return Objects.equals(getPlayersMap(), that.getPlayersMap());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayersMap());
	}
}
