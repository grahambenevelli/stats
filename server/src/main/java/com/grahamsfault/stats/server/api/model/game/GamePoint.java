package com.grahamsfault.stats.server.api.model.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamePoint {

	int num;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GamePoint)) return false;
		GamePoint gamePoint = (GamePoint) o;
		return num == gamePoint.num;
	}

	@Override
	public int hashCode() {
		return Objects.hash(num);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("num", num)
				.toString();
	}
}
