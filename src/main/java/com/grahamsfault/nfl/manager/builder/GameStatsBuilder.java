package com.grahamsfault.nfl.manager.builder;


import com.google.common.collect.Maps;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.nfl.model.GameStats;
import com.grahamsfault.nfl.model.PlayerStats;

import java.util.Map;

public class GameStatsBuilder {

	private final Map<String, PlayerStats.Builder> statsMap;

	public GameStatsBuilder() {
		this.statsMap = Maps.newHashMap();
	}

	public void addPassing(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combinePassingStats(key, statsMap.get(key), value));
		} else {
			statsMap.put(key, translatePassingStats(key, value));
		}
	}

	public void addRushing(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineRushingStats(key, statsMap.get(key), value));
		} else {
			statsMap.put(key, translateRushingStats(key, value));
		}
	}

	public void addReceiving(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineReceivingStats(key, statsMap.get(key), value));
		} else {
			statsMap.put(key, translateReceivingStats(key, value));
		}
	}

	public void addFumbles(String key, RawStats value) {
		if (statsMap.containsKey(key)) {
			statsMap.put(key, combineFumbleStats(key, statsMap.get(key), value));
		} else {
			statsMap.put(key, translateFumbleStats(key, value));
		}
	}

	private PlayerStats.Builder combineRushingStats(String key, PlayerStats.Builder builder, RawStats value) {
		builder.rushingAttempts(value.getAtt());
		builder.rushingYards(value.getYds());
		builder.rushingTouchdowns(value.getTds());
		builder.rushingLong(value.getLng());
		builder.rushingLongTouchdown(value.getLngtd());

		return builder;
	}

	protected PlayerStats.Builder translateRushingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.rushingAttempts(value.getAtt());
		builder.rushingYards(value.getYds());
		builder.rushingTouchdowns(value.getTds());
		builder.rushingLong(value.getLng());
		builder.rushingLongTouchdown(value.getLngtd());

		return builder;
	}

	private PlayerStats.Builder combinePassingStats(String key, PlayerStats.Builder builder, RawStats value) {
		builder.passingAttempts(value.getAtt());
		builder.passingCompletions(value.getCmp());
		builder.passingYards(value.getYds());
		builder.passingTouchdowns(value.getTds());
		builder.interceptions(value.getInts());

		return builder;
	}

	protected PlayerStats.Builder translatePassingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.passingAttempts(value.getAtt());
		builder.passingCompletions(value.getCmp());
		builder.passingYards(value.getYds());
		builder.passingTouchdowns(value.getTds());
		builder.interceptions(value.getInts());

		return builder;
	}

	public PlayerStats.Builder translateReceivingStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.receptions(value.getRec());
		builder.receivingYards(value.getYds());
		builder.receivingTouchdowns(value.getTds());
		builder.receivingLong(value.getLng());
		builder.receivingLongTouchdown(value.getLngtd());

		return builder;
	}

	private PlayerStats.Builder combineReceivingStats(String key, PlayerStats.Builder builder, RawStats value) {
		builder.receptions(value.getRec());
		builder.receivingYards(value.getYds());
		builder.receivingTouchdowns(value.getTds());
		builder.receivingLong(value.getLng());
		builder.receivingLongTouchdown(value.getLngtd());

		return builder;
	}

	public PlayerStats.Builder translateFumbleStats(String key, RawStats value) {
		PlayerStats.Builder builder = PlayerStats.builder(value.getName(), key);

		builder.fumbles(value.getTot());
		builder.fumblesLost(value.getLost());
		builder.fumblesRecovered(value.getRcv());
		builder.fumbleYards(value.getYds());

		return builder;
	}

	private PlayerStats.Builder combineFumbleStats(String key, PlayerStats.Builder builder, RawStats value) {
		builder.fumbles(value.getTot());
		builder.fumblesLost(value.getLost());
		builder.fumblesRecovered(value.getRcv());
		builder.fumbleYards(value.getYds());

		return builder;
	}

	public GameStats build() {
		Map<String, PlayerStats> playerStatsMap = Maps.newHashMap();
		for (Map.Entry<String, PlayerStats.Builder> entry : statsMap.entrySet()) {
			playerStatsMap.put(entry.getKey(), entry.getValue().build());
		}

		return new GameStats(playerStatsMap);
	}
}
