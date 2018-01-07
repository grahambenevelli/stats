package com.grahamsfault.stats.server.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableList;

import java.util.List;

public enum Team {
	ARIZONA("ARI", "Arizona", "Cardinals"),
	ATLANTA("ATL", "Atlanta", "Falcons"),
	BALTIMORE("BAL", "Baltimore", "Ravens"),
	BUFFALO("BUF", "Buffalo", "Bills"),
	CAROLINA("CAR", "Carolina", "Panthers"),
	CHICAGO("CHI", "Chicago", "Bears"),
	CINCINNATI("CIN", "Cincinnati", "Bengals"),
	CLEVELAND("CLE", "Cleveland", "Browns"),
	DALLAS("DAL", "Dallas", "Cowboys"),
	DENVER("DEN", "Denver", "Broncos"),
	DETROIT("DET", "Detroit", "Lions"),
	GREEN_BAY("GB", "Green Bay", "Packers"),
	HOUSTON("HOU", "Houston", "Texans"),
	INDIANAPOLIS("IND", "Indianapolis", "Colts"),
	JACKSONVILLE("JAC", "Jacksonville", "Jaguars"),
	KANSAS_CITY("KC", "Kansas City", "Chiefs"),
	LOS_ANGELES_RAMS(ImmutableList.of("LAR", "STL"), "Los Angeles", "Rams"),
	LOS_ANGELES_CHARGERS(ImmutableList.of("SD", "LAC"), "Los Angeles", "Chargers"),
	MIAMI("MIA", "Miami", "Dolphins"),
	MINNESOTA("MIN", "Minnesota", "Vikings"),
	NEW_ENGLAND("NE", "New England", "Patriots"),
	NEW_ORLEANS("NO", "New Orleans", "Saints"),
	NEW_YORK_GIANTS("NYG", "New York", "Giants"),
	NEW_YORK_JETS("NYJ", "New York", "Jets"),
	OAKLAND("OAK", "Oakland", "Raiders"),
	PHILADELPHIA("PHI", "Philadelphia", "Eagles"),
	PITTSBURGH("PIT", "Pittsburgh", "Steelers"),
	SEATTLE("SEA", "Seattle", "Seahawks"),
	SAN_FRANCISCO("SF", "San Francisco", "49ers"),
	TAMPA_BAY("TB", "Tampa Bay", "Buccaneers"),
	TENNESSEE("TEN", "Tennessee", "Titans"),
	WASHINGTON("WAS", "Washington", "Redskins"),
	FREE_AGENT("FA", null, null);

	public final String abbreviation;
	public final List<String> abbreviations;
	public final String location;
	public final String name;

	Team(String abbreviation, String location, String name) {
		this(ImmutableList.of(abbreviation), location, name);
	}

	Team(List<String> abbreviations, String location, String name) {
		this.abbreviation = abbreviations.stream().findFirst().get();
		this.abbreviations = abbreviations;
		this.location = location;
		this.name = name;
	}

	@JsonCreator
	public static Team forValue(String value) {
		for (Team team : Team.values()) {
			if (team.abbreviations.stream().anyMatch(s -> s.equals(value))) {
				return team;
			}
		}

		return FREE_AGENT;
	}
}
