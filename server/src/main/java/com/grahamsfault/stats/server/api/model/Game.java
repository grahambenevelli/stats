package com.grahamsfault.stats.server.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.stats.server.api.model.game.GameType;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

	private final Team away;
	private final Team home;

	private final int day;
	private final String eid;
	private final String gamekey;
	private final String meridiem;
	private final int month;
	private final GameType seasonType;
	private final String time;
	private final String wday;
	private final int week;
	private final int year;

	public Game(
			@JsonProperty("away") Team away,
			@JsonProperty("home") Team home,
			@JsonProperty("day") int day,
			@JsonProperty("eid") String eid,
			@JsonProperty("gamekey") String gamekey,
			@JsonProperty("meridiem") String meridiem,
			@JsonProperty("month") int month,
			@JsonProperty("season_type") GameType seasonType,
			@JsonProperty("time") String time,
			@JsonProperty("wday") String wday,
			@JsonProperty("week") int week,
			@JsonProperty("year") int year
	) {
		this.away = away;
		this.home = home;
		this.day = day;
		this.eid = eid;
		this.gamekey = gamekey;
		this.meridiem = meridiem;
		this.month = month;
		this.seasonType = seasonType;
		this.time = time;
		this.wday = wday;
		this.week = week;
		this.year = year;
	}

	public Team getAway() {
		return away;
	}

	public Team getHome() {
		return home;
	}

	public int getDay() {
		return day;
	}

	public String getEid() {
		return eid;
	}

	public String getGamekey() {
		return gamekey;
	}

	public String getMeridiem() {
		return meridiem;
	}

	public int getMonth() {
		return month;
	}

	public GameType getSeasonType() {
		return seasonType;
	}

	public String getTime() {
		return time;
	}

	public String getWday() {
		return wday;
	}

	public int getWeek() {
		return week;
	}

	public int getYear() {
		return year;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Game)) return false;
		Game game = (Game) o;
		return getDay() == game.getDay() &&
				getMonth() == game.getMonth() &&
				getWeek() == game.getWeek() &&
				getYear() == game.getYear() &&
				getAway() == game.getAway() &&
				getHome() == game.getHome() &&
				Objects.equals(getEid(), game.getEid()) &&
				Objects.equals(getGamekey(), game.getGamekey()) &&
				Objects.equals(getMeridiem(), game.getMeridiem()) &&
				Objects.equals(getSeasonType(), game.getSeasonType()) &&
				Objects.equals(getTime(), game.getTime()) &&
				Objects.equals(getWday(), game.getWday());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAway(), getHome(), getDay(), getEid(), getGamekey(), getMeridiem(), getMonth(), getSeasonType(), getTime(), getWday(), getWeek(), getYear());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("away", away)
				.add("home", home)
				.add("day", day)
				.add("eid", eid)
				.add("gamekey", gamekey)
				.add("meridiem", meridiem)
				.add("month", month)
				.add("seasonType", seasonType)
				.add("time", time)
				.add("wday", wday)
				.add("week", week)
				.add("year", year)
				.toString();
	}
}
