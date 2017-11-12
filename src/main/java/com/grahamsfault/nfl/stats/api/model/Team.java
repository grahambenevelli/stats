package com.grahamsfault.nfl.stats.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Team {
	ARIZONA("ARI", "Arizona", "Cardinals"),
	ATLANTA("ATL", "Atlanta", "Falcons"),
	BALTIMORE("BAL", "Baltimore", "Ravens"),
	BUFFALO("BUF", "Buffalo", "Bills"),
	CAROLINA("CAR", "Carolina", "Panthers"),
	CHICAGO("CHI", "Chicago", "Bears"),
	CINNCINATI("CIN", "Cincinnati", "Bengals"),
	CLEVELAND("CLE", "Cleveland", "Browns"),
	DALLAS("DAL", "Dallas", "Cowboys"),
	DENVER("DEN", "Denver", "Broncos"),
	DETROIT("DET", "Detroit", "Lions"),
	GREEN_BAY("GB", "Green Bay", "Packers"),
	HOUSTON("HOU", "Houston", "Texans"),
	INDIANAPOLIS("IND", "Indianapolis", "Colts"),
	JACKSONVILLE("JAC", "Jacksonville", "Jaguars"),
	KANSAS_CITY("KC", "Kansas City", "Chiefs"),
	LOS_ANGELES("LA", "Los Angeles", "Rams"),
	MIAMI("MIA", "Miami", "Dolphins"),
	MINNESOTA("MIN", "Minnesota", "Vikings"),
	NEW_ENGLAND("NE", "New England", "Patriots"),
	NEW_ORLEANS("NO", "New Orleans", "Saints"),
	NEW_YORK_GIANTS("NYG", "New York", "Giants"),
	NEW_YORK_JETS("NYJ", "New York", "Jets"),
	OAKLAND("OAK", "Oakland", "Raiders"),
	PHILADELPHIA("PHI", "Philadelphia", "Eagles"),
	PITTSBURGH("PIT", "Pittsburgh", "Steelers"),
	SAN_DIEGO("SD", "San Diego", "Chargers"),
	SEATTLE("SEA", "Seattle", "Seahawks"),
	SAN_FRANCISCO("SF", "San Francisco", "49ers"),
	TAMPA_BAY("TB", "Tampa Bay", "Buccaneers"),
	TENNESSEE("TEN", "Tennessee", "Titans"),
	WASHINGTON("WAS", "Washington", "Redskins"),
	FREE_AGENT("FA", null, null);

	public final String abbreviation;
	public final String location;
	public final String name;

	Team(String abbreviation, String location, String name) {
		this.abbreviation = abbreviation;
		this.location = location;
		this.name = name;
	}

	@JsonCreator
	public static Team forValue(String value) {
		for (Team team : Team.values()) {
			if (team.abbreviation.equals(value)) {
				return team;
			}
		}

		return FREE_AGENT;
	}
}
