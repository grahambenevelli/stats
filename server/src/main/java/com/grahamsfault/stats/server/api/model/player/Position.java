package com.grahamsfault.stats.server.api.model.player;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Position {
	QB("QB", "Quarterback"),
	RB("RB", "Running Back"),
	WR("WR", "Wide Receiver"),
	TE("TE", "Tight End");

	public final String abbreviation;
	public final String name;

	Position(String abbreviation, String name) {
		this.abbreviation = abbreviation;
		this.name = name;
	}

	@JsonCreator
	public static Position forValue(String value) {
		for (Position position : Position.values()) {
			if (position.abbreviation.equals(value)) {
				return position;
			}
		}

		return null;
	}
}
