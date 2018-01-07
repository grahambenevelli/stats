package com.grahamsfault.nfl.api.model.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawStats {

	private String playerId;

	private final String name;
	private final Integer att;
	private final Integer cmp;
	private final Integer rec;
	private final Integer tot;
	private final Integer rcv;
	private final Integer trcv;
	private final Integer lost;
	private final Integer yds;
	private final Integer tds;
	private final Integer ints;
	private final Integer lng;
	private final Integer lngtd;
	private final Integer twopta;
	private final Integer twoptm;

	@JsonCreator
	public RawStats(
			@JsonProperty("name") String name,
			@JsonProperty("att") Integer att,
			@JsonProperty("cmp") Integer cmp,
			@JsonProperty("rec") Integer rec,
			@JsonProperty("tot") Integer tot,
			@JsonProperty("rcv") Integer rcv,
			@JsonProperty("trcv") Integer trcv,
			@JsonProperty("lost") Integer lost,
			@JsonProperty("yds") Integer yds,
			@JsonProperty("tds") Integer tds,
			@JsonProperty("ints") Integer ints,
			@JsonProperty("lng") Integer lng,
			@JsonProperty("lngtd") Integer lngtd,
			@JsonProperty("twopta") Integer twopta,
			@JsonProperty("twoptm") Integer twoptm) {
		this.name = name;
		this.att = att;
		this.cmp = cmp;
		this.rec = rec;
		this.tot = tot;
		this.rcv = rcv;
		this.trcv = trcv;
		this.lost = lost;
		this.yds = yds;
		this.tds = tds;
		this.ints = ints;
		this.lng = lng;
		this.lngtd = lngtd;
		this.twopta = twopta;
		this.twoptm = twoptm;
	}

	public String getPlayerId() {
		return playerId;
	}

	public RawStats setPlayerId(String playerId) {
		this.playerId = playerId;
		return this;
	}

	public String getName() {
		return name;
	}

	public Integer getAtt() {
		return att;
	}

	public Integer getCmp() {
		return cmp;
	}

	public Integer getYds() {
		return yds;
	}

	public Integer getTds() {
		return tds;
	}

	public Integer getInts() {
		return ints;
	}

	public Integer getLng() {
		return lng;
	}

	public Integer getLngtd() {
		return lngtd;
	}

	public Integer getTwopta() {
		return twopta;
	}

	public Integer getTwoptm() {
		return twoptm;
	}

	public Integer getRec() {
		return rec;
	}

	public Integer getTot() {
		return tot;
	}

	public Integer getRcv() {
		return rcv;
	}

	public Integer getTrcv() {
		return trcv;
	}

	public Integer getLost() {
		return lost;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("playerId", playerId)
				.add("name", name)
				.add("att", att)
				.add("cmp", cmp)
				.add("rec", rec)
				.add("tot", tot)
				.add("rcv", rcv)
				.add("trcv", trcv)
				.add("lost", lost)
				.add("yds", yds)
				.add("tds", tds)
				.add("ints", ints)
				.add("lng", lng)
				.add("lngtd", lngtd)
				.add("twopta", twopta)
				.add("twoptm", twoptm)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RawStats)) return false;
		RawStats rawStats = (RawStats) o;
		return Objects.equals(getPlayerId(), rawStats.getPlayerId()) &&
				Objects.equals(getName(), rawStats.getName()) &&
				Objects.equals(getAtt(), rawStats.getAtt()) &&
				Objects.equals(getCmp(), rawStats.getCmp()) &&
				Objects.equals(getRec(), rawStats.getRec()) &&
				Objects.equals(getTot(), rawStats.getTot()) &&
				Objects.equals(getRcv(), rawStats.getRcv()) &&
				Objects.equals(getTrcv(), rawStats.getTrcv()) &&
				Objects.equals(getLost(), rawStats.getLost()) &&
				Objects.equals(getYds(), rawStats.getYds()) &&
				Objects.equals(getTds(), rawStats.getTds()) &&
				Objects.equals(getInts(), rawStats.getInts()) &&
				Objects.equals(getLng(), rawStats.getLng()) &&
				Objects.equals(getLngtd(), rawStats.getLngtd()) &&
				Objects.equals(getTwopta(), rawStats.getTwopta()) &&
				Objects.equals(getTwoptm(), rawStats.getTwoptm());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayerId(), getName(), getAtt(), getCmp(), getRec(), getTot(), getRcv(), getTrcv(), getLost(), getYds(), getTds(), getInts(), getLng(), getLngtd(), getTwopta(), getTwoptm());
	}
}
