package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameType;

import java.sql.SQLException;

/**
 * Class for consuming a Game object from a ResultSet
 */
public class GameConsumer extends AbstractConsumer<Game> {

	private String away;
	private String home;
	private String seasonType;
	private String day;
	private String eid;
	private String gameKey;
	private String meridiem;
	private String month;
	private String time;
	private String wday;
	private String week;
	private String year;

	private GameConsumer() {
		this.away = "away";
		this.home = "home";
		this.seasonType = "season_type";
		this.day = "day";
		this.eid = "eid";
		this.gameKey = "gamekey";
		this.meridiem = "meridiem";
		this.month = "month";
		this.time = "time";
		this.wday = "wday";
		this.week = "week";
		this.year = "year";
	}

	public static GameConsumer consumer() {
		return new GameConsumer();
	}

	public GameConsumer setAway(String away) {
		this.away = away;
		return this;
	}

	public GameConsumer setHome(String home) {
		this.home = home;
		return this;
	}

	public GameConsumer setSeasonType(String seasonType) {
		this.seasonType = seasonType;
		return this;
	}

	public GameConsumer setDay(String day) {
		this.day = day;
		return this;
	}

	public GameConsumer setEid(String eid) {
		this.eid = eid;
		return this;
	}

	public GameConsumer setGameKey(String gameKey) {
		this.gameKey = gameKey;
		return this;
	}

	public GameConsumer setMeridiem(String meridiem) {
		this.meridiem = meridiem;
		return this;
	}

	public GameConsumer setMonth(String month) {
		this.month = month;
		return this;
	}

	public GameConsumer setTime(String time) {
		this.time = time;
		return this;
	}

	public GameConsumer setWday(String wday) {
		this.wday = wday;
		return this;
	}

	public GameConsumer setWeek(String week) {
		this.week = week;
		return this;
	}

	public GameConsumer setYear(String year) {
		this.year = year;
		return this;
	}

	@Override
	public Game consume(ReadOnlyResultSet result) throws SQLException {
		String away = result.getString(this.away);
		String home = result.getString(this.home);
		String seasonType = result.getString(this.seasonType);

		return new Game(
				Team.forValue(away),
				Team.forValue(home),
				result.getInt(day),
				result.getString(eid),
				result.getString(gameKey),
				result.getString(meridiem),
				result.getInt(month),
				GameType.forValue(seasonType),
				result.getString(time),
				result.getString(wday),
				result.getInt(week),
				result.getInt(year)
		);
	}
}