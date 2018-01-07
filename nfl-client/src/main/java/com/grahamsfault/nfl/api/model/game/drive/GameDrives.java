package com.grahamsfault.nfl.api.model.game.drive;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDrives {

	@JsonProperty("crntdrv")
	private int crntdrv;

	private final List<Drive> drives = Lists.newArrayList();

	@JsonAnySetter
	public void setDynamicProperty(String index, Drive drive) {
		if (Integer.parseInt(index) != drives.size() + 1) {
			throw new NullPointerException();
		}

		drives.add(drive);
	}

	public int getCrntdrv() {
		return crntdrv;
	}

	public void setCrntdrv(int crntdrv) {
		this.crntdrv = crntdrv;
	}

	public List<Drive> getDrives() {
		return drives;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("drives", drives)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameDrives)) return false;
		GameDrives that = (GameDrives) o;
		return Objects.equals(getDrives(), that.getDrives());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getDrives());
	}
}
