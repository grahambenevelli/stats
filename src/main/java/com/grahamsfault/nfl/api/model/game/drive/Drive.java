package com.grahamsfault.nfl.api.model.game.drive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GamePoint;
import com.grahamsfault.nfl.api.model.game.drive.play.PlayWrapper;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Drive {

	private final Team posteam;
	private final Integer quarter;
	private final boolean redzone;
	private final PlayWrapper plays;
	private final Integer fds;
	private final String result;
	private final Integer penyds;
	private final Integer ydsgained;
	private final Integer numplays;
	private final String postime;
	private final GamePoint start;
	private final GamePoint end;

	@JsonCreator
	public Drive(
			@JsonProperty("posteam") Team posteam,
			@JsonProperty("qtr") Integer quarter,
			@JsonProperty("redzone") boolean redzone,
			@JsonProperty("plays") PlayWrapper plays,
			@JsonProperty("fds") Integer fds,
			@JsonProperty("result") String result,
			@JsonProperty("penyds") Integer penyds,
			@JsonProperty("ydsgained") Integer ydsgained,
			@JsonProperty("numplays") Integer numplays,
			@JsonProperty("postime") String postime,
			@JsonProperty("start") GamePoint start,
			@JsonProperty("end") GamePoint end) {
		this.posteam = posteam;
		this.quarter = quarter;
		this.redzone = redzone;
		this.plays = plays;
		this.fds = fds;
		this.result = result;
		this.penyds = penyds;
		this.ydsgained = ydsgained;
		this.numplays = numplays;
		this.postime = postime;
		this.start = start;
		this.end = end;
	}

	public Team getPosteam() {
		return posteam;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public boolean isRedzone() {
		return redzone;
	}

	public PlayWrapper getPlays() {
		return plays;
	}

	public Integer getFds() {
		return fds;
	}

	public String getResult() {
		return result;
	}

	public Integer getPenyds() {
		return penyds;
	}

	public Integer getYdsgained() {
		return ydsgained;
	}

	public Integer getNumplays() {
		return numplays;
	}

	public String getPostime() {
		return postime;
	}

	public GamePoint getStart() {
		return start;
	}

	public GamePoint getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("posteam", posteam)
				.add("quarter", quarter)
				.add("redzone", redzone)
				.add("plays", plays)
				.add("fds", fds)
				.add("result", result)
				.add("penyds", penyds)
				.add("ydsgained", ydsgained)
				.add("numplays", numplays)
				.add("postime", postime)
				.add("start", start)
				.add("end", end)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Drive)) return false;
		Drive drive = (Drive) o;
		return isRedzone() == drive.isRedzone() &&
				getPosteam() == drive.getPosteam() &&
				Objects.equals(getQuarter(), drive.getQuarter()) &&
				Objects.equals(getPlays(), drive.getPlays()) &&
				Objects.equals(getFds(), drive.getFds()) &&
				Objects.equals(getResult(), drive.getResult()) &&
				Objects.equals(getPenyds(), drive.getPenyds()) &&
				Objects.equals(getYdsgained(), drive.getYdsgained()) &&
				Objects.equals(getNumplays(), drive.getNumplays()) &&
				Objects.equals(getPostime(), drive.getPostime()) &&
				Objects.equals(getStart(), drive.getStart()) &&
				Objects.equals(getEnd(), drive.getEnd());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPosteam(), getQuarter(), isRedzone(), getPlays(), getFds(), getResult(), getPenyds(), getYdsgained(), getNumplays(), getPostime(), getStart(), getEnd());
	}
}
