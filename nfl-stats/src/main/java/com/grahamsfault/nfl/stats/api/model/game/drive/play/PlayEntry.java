package com.grahamsfault.nfl.stats.api.model.game.drive.play;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.stats.api.model.Team;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayEntry {

	private final Integer sp;
	private final Integer qtr;
	private final Integer down;
	private final String time;
	private final String yardLine;
	private final Integer yardsTogo;
	private final Integer yardsNet;
	private final Team posteam;
	private final String desc;
	private final String note;
	private final PlayPlayersWrapper players;

	public PlayEntry(
			@JsonProperty("sp") Integer sp,
			@JsonProperty("qtr") Integer qtr,
			@JsonProperty("down") Integer down,
			@JsonProperty("time") String time,
			@JsonProperty("yrdln") String yrdln,
			@JsonProperty("ydstogo") Integer ydstogo,
			@JsonProperty("ydsnet") Integer ydsnet,
			@JsonProperty("posteam") Team posteam,
			@JsonProperty("desc") String desc,
			@JsonProperty("note") String note,
			@JsonProperty("players") PlayPlayersWrapper players) {
		this.sp = sp;
		this.qtr = qtr;
		this.down = down;
		this.time = time;
		this.yardLine = yrdln;
		this.yardsTogo = ydstogo;
		this.yardsNet = ydsnet;
		this.posteam = posteam;
		this.desc = desc;
		this.note = note;
		this.players = players;
	}

	public Integer getSp() {
		return sp;
	}

	public Integer getQtr() {
		return qtr;
	}

	public Integer getDown() {
		return down;
	}

	public String getTime() {
		return time;
	}

	public String getYardLine() {
		return yardLine;
	}

	public Integer getYardsTogo() {
		return yardsTogo;
	}

	public Integer getYardsNet() {
		return yardsNet;
	}

	public Team getPosteam() {
		return posteam;
	}

	public String getDesc() {
		return desc;
	}

	public String getNote() {
		return note;
	}

	public PlayPlayersWrapper getPlayers() {
		return players;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("sp", sp)
				.add("qtr", qtr)
				.add("down", down)
				.add("time", time)
				.add("yardLine", yardLine)
				.add("yardsTogo", yardsTogo)
				.add("yardsNet", yardsNet)
				.add("posteam", posteam)
				.add("desc", desc)
				.add("note", note)
				.add("players", players)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayEntry)) return false;
		PlayEntry playEntry = (PlayEntry) o;
		return Objects.equals(getSp(), playEntry.getSp()) &&
				Objects.equals(getQtr(), playEntry.getQtr()) &&
				Objects.equals(getDown(), playEntry.getDown()) &&
				Objects.equals(getTime(), playEntry.getTime()) &&
				Objects.equals(getYardLine(), playEntry.getYardLine()) &&
				Objects.equals(getYardsTogo(), playEntry.getYardsTogo()) &&
				Objects.equals(getYardsNet(), playEntry.getYardsNet()) &&
				getPosteam() == playEntry.getPosteam() &&
				Objects.equals(getDesc(), playEntry.getDesc()) &&
				Objects.equals(getNote(), playEntry.getNote()) &&
				Objects.equals(getPlayers(), playEntry.getPlayers());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSp(), getQtr(), getDown(), getTime(), getYardLine(), getYardsTogo(), getYardsNet(), getPosteam(), getDesc(), getNote(), getPlayers());
	}
}
