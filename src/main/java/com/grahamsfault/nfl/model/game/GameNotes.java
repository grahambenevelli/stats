package com.grahamsfault.nfl.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.model.Team;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameNotes {

	private final String qtr;
	private final String weather;
	private final String media;
	private final String yl;
	private final String note;
	private final String down;
	private final String togo;
	private final boolean redzone;
	private final String clock;
	private final Team posteam;
	private final String stadium;

	@JsonCreator
	public GameNotes(
			@JsonProperty("qtr") String qtr,
			@JsonProperty("weather") String weather,
			@JsonProperty("media") String media,
			@JsonProperty("yl") String yl,
			@JsonProperty("note") String note,
			@JsonProperty("down") String down,
			@JsonProperty("togo") String togo,
			@JsonProperty("redzone") boolean redzone,
			@JsonProperty("clock") String clock,
			@JsonProperty("posteam") Team posteam,
			@JsonProperty("stadium") String stadium) {
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

	public String getDown() {
		return down;
	}

	public String getTogo() {
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
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameNotes)) return false;
		GameNotes gameNotes = (GameNotes) o;
		return redzone == gameNotes.redzone &&
				Objects.equals(qtr, gameNotes.qtr) &&
				Objects.equals(weather, gameNotes.weather) &&
				Objects.equals(media, gameNotes.media) &&
				Objects.equals(yl, gameNotes.yl) &&
				Objects.equals(note, gameNotes.note) &&
				Objects.equals(down, gameNotes.down) &&
				Objects.equals(togo, gameNotes.togo) &&
				Objects.equals(clock, gameNotes.clock) &&
				posteam == gameNotes.posteam &&
				Objects.equals(stadium, gameNotes.stadium);
	}

	@Override
	public int hashCode() {
		return Objects.hash(qtr, weather, media, yl, note, down, togo, redzone, clock, posteam, stadium);
	}
}
