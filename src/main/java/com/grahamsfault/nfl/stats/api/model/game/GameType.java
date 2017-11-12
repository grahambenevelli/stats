package com.grahamsfault.nfl.stats.api.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GameType {
	REG("REG"),
	PRE("PRE"),
	POST("POST");

	public final String type;

	GameType(String type) {
		this.type = type;
	}

	@JsonCreator
	public static GameType forValue(String value) {
		for (GameType gameType : GameType.values()) {
			if (gameType.type.equals(value)) {
				return gameType;
			}
		}

		throw new IllegalStateException("GameType value not recognized " + value);
	}
}
