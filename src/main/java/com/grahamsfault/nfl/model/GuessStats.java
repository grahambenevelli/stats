package com.grahamsfault.nfl.model;

/**
 * Stats that are more of a guess, they are not real stats and shows something like an average
 */
public abstract class GuessStats extends BaseStats<Double> {
	protected GuessStats(Double passingAttempts, Double passingCompletions, Double passingYards, Double passingTouchdowns, Double interceptions, Double rushingAttempts, Double rushingYards, Double rushingTouchdowns, Double rushingLong, Double rushingLongTouchdown, Double receptions, Double receivingYards, Double receivingTouchdowns, Double receivingLong, Double receivingLongTouchdown, Double fumbles, Double fumblesLost, Double fumblesRecovered, Double fumbleYards) {
		super(passingAttempts, passingCompletions, passingYards, passingTouchdowns, interceptions, rushingAttempts, rushingYards, rushingTouchdowns, rushingLong, rushingLongTouchdown, receptions, receivingYards, receivingTouchdowns, receivingLong, receivingLongTouchdown, fumbles, fumblesLost, fumblesRecovered, fumbleYards);
	}
}
