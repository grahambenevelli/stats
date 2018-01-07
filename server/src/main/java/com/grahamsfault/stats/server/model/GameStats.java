package com.grahamsfault.stats.server.model;

import com.google.common.base.MoreObjects;
import com.grahamsfault.stats.server.api.model.game.GameNotes;
import com.grahamsfault.stats.server.api.model.player.RawStats;
import com.grahamsfault.stats.server.manager.builder.GameStatsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class GameStats {

	private final static Logger LOG = LoggerFactory.getLogger(GameStats.class);

	private final Map<String, PlayerStats> playerStatsMap;

	public GameStats(Map<String, PlayerStats> playerStatsMap) {
		this.playerStatsMap = playerStatsMap;
	}

	public Map<String, PlayerStats> getPlayerStatsMap() {
		return playerStatsMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameStats)) return false;
		GameStats gameStats = (GameStats) o;
		return Objects.equals(getPlayerStatsMap(), gameStats.getPlayerStatsMap());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayerStatsMap());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("playerStatsMap", playerStatsMap)
				.toString();
	}

	/**
	 * Convert the games notes into the game stats object
	 *
	 * @param gameNotes The game's notes returned from the NFL service
	 * @return The game stats
	 * TODO test
	 */
	public static GameStats fromGameNotes(String eid, GameNotes gameNotes) {
		GameStatsBuilder gameStatsFactory = new GameStatsBuilder();

		if (gameNotes.getHome().getTeamStats().getPassing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getPassing().getStats().entrySet()) {
				gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing passing stats for home team for game " + eid);
		}

		if (gameNotes.getHome().getTeamStats().getRushing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getRushing().getStats().entrySet()) {
				gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing rushing stats for home team for game " + eid);
		}

		if (gameNotes.getHome().getTeamStats().getReceiving() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getReceiving().getStats().entrySet()) {
				gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing receiving stats for home team for game " + eid);
		}

		if (gameNotes.getHome().getTeamStats().getFumbles() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getFumbles().getStats().entrySet()) {
				gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing fumble stats for home team for game " + eid);
		}

		if (gameNotes.getAway().getTeamStats().getPassing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getPassing().getStats().entrySet()) {
				gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing passing stats for away team for game " + eid);
		}

		if (gameNotes.getAway().getTeamStats().getRushing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getRushing().getStats().entrySet()) {
				gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing rushing stats for away team for game " + eid);
		}

		if (gameNotes.getAway().getTeamStats().getReceiving() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getReceiving().getStats().entrySet()) {
				gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing receiving stats for away team for game " + eid);
		}

		if (gameNotes.getAway().getTeamStats().getFumbles() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getFumbles().getStats().entrySet()) {
				gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
			}
		} else {
			LOG.warn("Missing fumble stats for away team for game " + eid);
		}

		return gameStatsFactory.build();
	}
}
