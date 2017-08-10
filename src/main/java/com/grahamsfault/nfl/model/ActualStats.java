package com.grahamsfault.nfl.model;

/**
 * An abstract class to represent actual stats of a player
 */
public abstract class ActualStats extends BaseStats<Long> {
	protected ActualStats(Long passingAttempts, Long passingCompletions, Long passingYards, Long passingTouchdowns, Long interceptions, Long rushingAttempts, Long rushingYards, Long rushingTouchdowns, Long rushingLong, Long rushingLongTouchdown, Long receptions, Long receivingYards, Long receivingTouchdowns, Long receivingLong, Long receivingLongTouchdown, Long fumbles, Long fumblesLost, Long fumblesRecovered, Long fumbleYards) {
		super(passingAttempts, passingCompletions, passingYards, passingTouchdowns, interceptions, rushingAttempts, rushingYards, rushingTouchdowns, rushingLong, rushingLongTouchdown, receptions, receivingYards, receivingTouchdowns, receivingLong, receivingLongTouchdown, fumbles, fumblesLost, fumblesRecovered, fumbleYards);
	}
}
