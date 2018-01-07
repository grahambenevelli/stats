package com.grahamsfault.stats.server.manager.builder;


import com.google.common.collect.Maps;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.stats.server.model.GameStats;
import com.grahamsfault.stats.server.model.PlayerStats;

import java.util.Map;

/**
 * A builder class to create game stats objects
 */
public class GameStatsBuilder {

	private final Map<String, PlayerStats.Builder> statsMap;

	public GameStatsBuilder() {
		this.statsMap = Maps.newHashMap();
	}

	/**
	 * Add passing yards to the game stats map
	 *
	 * @param key The player key
	 * @param value The passing stats
	 * TODO test
	 */
	public void addPassing(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combinePassingStats(statsMap.get(key), value));
		} else {
			statsMap.put(key, translatePassingStats(key, value));
		}
	}

	/**
	 * Add the rushing stats to the game stats map
	 *
	 * @param key The player key
	 * @param value The rushing stats
	 * TODO test
	 */
	public void addRushing(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineRushingStats(statsMap.get(key), value));
		} else {
			statsMap.put(key, translateRushingStats(key, value));
		}
	}

	/**
	 * Add the receiving stats to the game stats map
	 *
	 * @param key The player key
	 * @param value The receiving stats
	 * TODO test
	 */
	public void addReceiving(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineReceivingStats(statsMap.get(key), value));
		} else {
			statsMap.put(key, translateReceivingStats(key, value));
		}
	}

	/**
	 * Add the fumbles stats to the game stats map
	 *
	 * @param key The player key
	 * @param value The fumble stats
	 * TODO test
	 *
	 */
	public void addFumbles(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineFumbleStats(statsMap.get(key), value));
		} else {
			statsMap.put(key, translateFumbleStats(key, value));
		}
	}

	/**
	 * Increment the rushing stats already stored for a given player
	 *
	 * @param builder The player's stats builder
	 * @param value The stats
	 * @return The builder object
	 */
	private PlayerStats.Builder combineRushingStats(PlayerStats.Builder builder, RawStats value) {
		builder.rushingAttempts(value.getAtt());
		builder.rushingYards(value.getYds());
		builder.rushingTouchdowns(value.getTds());
		builder.rushingLong(value.getLng());
		if (value.getLngtd() != null) {
			builder.rushingLongTouchdown(value.getLngtd());
		}

		return builder;
	}

	/**
	 * Ingest the rushing stats into the player builder
	 *
	 * @param key The player key
	 * @param value The stats value
	 * @return The new builder object
	 */
	protected PlayerStats.Builder translateRushingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.rushingAttempts(value.getAtt());
		builder.rushingYards(value.getYds());
		builder.rushingTouchdowns(value.getTds());
		builder.rushingLong(value.getLng());
		if (value.getLngtd() != null) {
			builder.rushingLongTouchdown(value.getLngtd());
		}

		return builder;
	}

	/**
	 * Increment the passing stats already stored for a given player
	 *
	 * @param builder The player's stats builder
	 * @param value The stats
	 * @return The builder object
	 */
	private PlayerStats.Builder combinePassingStats(PlayerStats.Builder builder, RawStats value) {
		builder.passingAttempts(value.getAtt());
		builder.passingCompletions(value.getCmp());
		builder.passingYards(value.getYds());
		builder.passingTouchdowns(value.getTds());
		builder.interceptions(value.getInts());

		return builder;
	}

	/**
	 * Ingest the passing stats into the player builder
	 *
	 * @param key The player key
	 * @param value The stats value
	 * @return The new builder object
	 */
	protected PlayerStats.Builder translatePassingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.passingAttempts(value.getAtt());
		builder.passingCompletions(value.getCmp());
		builder.passingYards(value.getYds());
		builder.passingTouchdowns(value.getTds());
		builder.interceptions(value.getInts());

		return builder;
	}

	/**
	 * Increment the receiving stats already stored for a given player
	 *
	 * @param builder The player's stats builder
	 * @param value The stats
	 * @return The builder object
	 */
	private PlayerStats.Builder combineReceivingStats(PlayerStats.Builder builder, RawStats value) {
		builder.receptions(value.getRec());
		builder.receivingYards(value.getYds());
		builder.receivingTouchdowns(value.getTds());
		builder.receivingLong(value.getLng());
		if (value.getLngtd() != null) {
			builder.receivingLongTouchdown(value.getLngtd());
		}

		return builder;
	}

	/**
	 * Ingest the receiving stats into the player builder
	 *
	 * @param key The player key
	 * @param value The stats value
	 * @return The new builder object
	 */
	public PlayerStats.Builder translateReceivingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.receptions(value.getRec());
		builder.receivingYards(value.getYds());
		builder.receivingTouchdowns(value.getTds());
		builder.receivingLong(value.getLng());
		if (value.getLngtd() != null) {
			builder.receivingLongTouchdown(value.getLngtd());
		}

		return builder;
	}

	/**
	 * Ingest the fumble stats into the player builder
	 *
	 * @param key The player key
	 * @param value The stats value
	 * @return The new builder object
	 */
	public PlayerStats.Builder translateFumbleStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.fumbles(value.getTot());
		builder.fumblesLost(value.getLost());
		builder.fumblesRecovered(value.getRcv());
		builder.fumbleYards(value.getYds());

		return builder;
	}

	/**
	 * Increment the fumble stats already stored for a given player
	 *
	 * @param builder The player's stats builder
	 * @param value The stats
	 * @return The builder object
	 */
	private PlayerStats.Builder combineFumbleStats(PlayerStats.Builder builder, RawStats value) {
		builder.fumbles(value.getTot());
		builder.fumblesLost(value.getLost());
		builder.fumblesRecovered(value.getRcv());
		builder.fumbleYards(value.getYds());

		return builder;
	}

	/**
	 * Build the GameStats object
	 *
	 * @return The GameStats
	 */
	public GameStats build() {
		Map<String, PlayerStats> playerStatsMap = Maps.newHashMap();
		for (Map.Entry<String, PlayerStats.Builder> entry : statsMap.entrySet()) {
			playerStatsMap.put(entry.getKey(), entry.getValue().build());
		}

		return new GameStats(playerStatsMap);
	}
}
