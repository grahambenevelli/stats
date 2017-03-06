package com.grahamsfault.nfl.api.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.api.model.game.drive.GameDrives;
import com.grahamsfault.nfl.api.model.game.team.GameTeamNotes;
import com.grahamsfault.nfl.api.model.Team;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameNotes {

	private final String qtr;
	private final String weather;
	private final String media;
	private final String yl;
	private final String note;
	private final Integer down;
	private final Integer togo;
	private final boolean redzone;
	private final String clock;
	private final Team posteam;
	private final String stadium;
	private final GameTeamNotes home;
	private final GameTeamNotes away;
	private final GameDrives drives;

	@JsonCreator
	public GameNotes(
			@JsonProperty("qtr") String qtr,
			@JsonProperty("weather") String weather,
			@JsonProperty("media") String media,
			@JsonProperty("yl") String yl,
			@JsonProperty("note") String note,
			@JsonProperty("down") Integer down,
			@JsonProperty("togo") Integer togo,
			@JsonProperty("redzone") boolean redzone,
			@JsonProperty("clock") String clock,
			@JsonProperty("posteam") Team posteam,
			@JsonProperty("stadium") String stadium,
			@JsonProperty("home") GameTeamNotes home,
			@JsonProperty("away") GameTeamNotes away,
			@JsonProperty("drives") GameDrives drives) {
		this.qtr = qtr;
		this.weather = weather;
		this.media = media;
		this.yl = yl;
		this.note = note;
		this.down = down;
		this.togo = togo;
		this.redzone = redzone;
		this.clock = clock;
		this.posteam = posteam;
		this.stadium = stadium;
		this.home = home;
		this.away = away;
		this.drives = drives;
	}

	public String getQtr() {
		return qtr;
	}

	public String getWeather() {
		return weather;
	}

	public String getMedia() {
		return media;
	}

	public String getYl() {
		return yl;
	}

	public String getNote() {
		return note;
	}

	public Integer getDown() {
		return down;
	}

	public Integer getTogo() {
		return togo;
	}

	public boolean isRedzone() {
		return redzone;
	}

	public String getClock() {
		return clock;
	}

	public Team getPosteam() {
		return posteam;
	}

	public String getStadium() {
		return stadium;
	}

	public GameTeamNotes getHome() {
		return home;
	}

	public GameTeamNotes getAway() {
		return away;
	}

	public GameDrives getDrives() {
		return drives;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("qtr", qtr)
				.add("weather", weather)
				.add("media", media)
				.add("yl", yl)
				.add("note", note)
				.add("down", down)
				.add("togo", togo)
				.add("redzone", redzone)
				.add("clock", clock)
				.add("posteam", posteam)
				.add("stadium", stadium)
				.add("home", home)
				.add("away", away)
				.add("drives", drives)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameNotes)) return false;
		GameNotes gameNotes = (GameNotes) o;
		return isRedzone() == gameNotes.isRedzone() &&
				Objects.equals(getQtr(), gameNotes.getQtr()) &&
				Objects.equals(getWeather(), gameNotes.getWeather()) &&
				Objects.equals(getMedia(), gameNotes.getMedia()) &&
				Objects.equals(getYl(), gameNotes.getYl()) &&
				Objects.equals(getNote(), gameNotes.getNote()) &&
				Objects.equals(getDown(), gameNotes.getDown()) &&
				Objects.equals(getTogo(), gameNotes.getTogo()) &&
				Objects.equals(getClock(), gameNotes.getClock()) &&
				getPosteam() == gameNotes.getPosteam() &&
				Objects.equals(getStadium(), gameNotes.getStadium()) &&
				Objects.equals(getHome(), gameNotes.getHome()) &&
				Objects.equals(getAway(), gameNotes.getAway()) &&
				Objects.equals(getDrives(), gameNotes.getDrives());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getQtr(), getWeather(), getMedia(), getYl(), getNote(), getDown(), getTogo(), isRedzone(), getClock(), getPosteam(), getStadium(), getHome(), getAway(), getDrives());
	}
}
