package com.grahamsfault.nfl.stats.model;

/**
 * The abstract class the represents a stats storage mechanism
 * @param <T> The type of the stats, usually Long or Double
 */
public abstract class BaseStats<T extends Number> {

	private final T passingAttempts;
	private final T passingCompletions;
	private final T passingYards;
	private final T passingTouchdowns;
	private final T interceptions;
	private final T rushingAttempts;
	private final T rushingYards;
	private final T rushingTouchdowns;
	private final T rushingLong;
	private final T rushingLongTouchdown;
	private final T receptions;
	private final T receivingYards;
	private final T receivingTouchdowns;
	private final T receivingLong;
	private final T receivingLongTouchdown;
	private final T fumbles;
	private final T fumblesLost;
	private final T fumblesRecovered;
	private final T fumbleYards;

	protected BaseStats(T passingAttempts,
						T passingCompletions,
						T passingYards,
						T passingTouchdowns,
						T interceptions,
						T rushingAttempts,
						T rushingYards,
						T rushingTouchdowns,
						T rushingLong,
						T rushingLongTouchdown,
						T receptions,
						T receivingYards,
						T receivingTouchdowns,
						T receivingLong,
						T receivingLongTouchdown,
						T fumbles,
						T fumblesLost,
						T fumblesRecovered,
						T fumbleYards) {
		this.passingAttempts = passingAttempts;
		this.passingCompletions = passingCompletions;
		this.passingYards = passingYards;
		this.passingTouchdowns = passingTouchdowns;
		this.interceptions = interceptions;
		this.rushingAttempts = rushingAttempts;
		this.rushingYards = rushingYards;
		this.rushingTouchdowns = rushingTouchdowns;
		this.rushingLong = rushingLong;
		this.rushingLongTouchdown = rushingLongTouchdown;
		this.receptions = receptions;
		this.receivingYards = receivingYards;
		this.receivingTouchdowns = receivingTouchdowns;
		this.receivingLong = receivingLong;
		this.receivingLongTouchdown = receivingLongTouchdown;
		this.fumbles = fumbles;
		this.fumblesLost = fumblesLost;
		this.fumblesRecovered = fumblesRecovered;
		this.fumbleYards = fumbleYards;
	}

	public T getPassingAttempts() {
		return passingAttempts;
	}

	public T getPassingCompletions() {
		return passingCompletions;
	}

	public T getPassingYards() {
		return passingYards;
	}

	public T getPassingTouchdowns() {
		return passingTouchdowns;
	}

	public T getInterceptions() {
		return interceptions;
	}

	public T getRushingAttempts() {
		return rushingAttempts;
	}

	public T getRushingYards() {
		return rushingYards;
	}

	public T getRushingTouchdowns() {
		return rushingTouchdowns;
	}

	public T getRushingLong() {
		return rushingLong;
	}

	public T getRushingLongTouchdown() {
		return rushingLongTouchdown;
	}

	public T getReceptions() {
		return receptions;
	}

	public T getReceivingYards() {
		return receivingYards;
	}

	public T getReceivingTouchdowns() {
		return receivingTouchdowns;
	}

	public T getReceivingLong() {
		return receivingLong;
	}

	public T getReceivingLongTouchdown() {
		return receivingLongTouchdown;
	}

	public T getFumbles() {
		return fumbles;
	}

	public T getFumblesLost() {
		return fumblesLost;
	}

	public T getFumblesRecovered() {
		return fumblesRecovered;
	}

	public T getFumbleYards() {
		return fumbleYards;
	}
}
