package com.grahamsfault.stats.server.model;

import com.google.common.base.MoreObjects;

public class PlayerStats extends ActualStats {

	private final String name;
	private final String id;

	private PlayerStats(
			String name,
			String id,
			Long passingAttempts,
			Long passingCompletions,
			Long passingYards,
			Long passingTouchdowns,
			Long interceptions,
			Long rushingAttempts,
			Long rushingYards,
			Long rushingTouchdowns,
			Long rushingLong,
			Long rushingLongTouchdown,
			Long receptions,
			Long receivingYards,
			Long receivingTouchdowns,
			Long receivingLong,
			Long receivingLongTouchdown,
			Long fumbles,
			Long fumblesLost,
			Long fumblesRecovered,
			Long fumbleYards
	) {
		super(passingAttempts, passingCompletions, passingYards, passingTouchdowns, interceptions, rushingAttempts, rushingYards, rushingTouchdowns, rushingLong, rushingLongTouchdown, receptions, receivingYards, receivingTouchdowns, receivingLong, receivingLongTouchdown, fumbles, fumblesLost, fumblesRecovered, fumbleYards);
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerStats)) return false;
		PlayerStats that = (PlayerStats) o;
		return com.google.common.base.Objects.equal(getName(), that.getName()) &&
				com.google.common.base.Objects.equal(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(getName(), getId());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("id", id)
				.toString();
	}

	public static Builder builder(String name, String id) {
		return new Builder(name, id);
	}

	public static class Builder {

		private final String name;
		private final String id;
		private long passingAttempts;
		private long passingCompletions;
		private long passingYards;
		private long passingTouchdowns;
		private long interceptions;
		private long rushingAttempts;
		private long rushingYards;
		private long rushingTouchdowns;
		private long rushingLong;
		private long rushingLongTouchdown;
		private long receptions;
		private long receivingYards;
		private long receivingTouchdowns;
		private long receivingLong;
		private long receivingLongTouchdown;
		private long fumbles;
		private long fumblesLost;
		private long fumblesRecovered;
		private long fumbleYards;

		public Builder(String name, String id) {
			this.name = name;
			this.id = id;
		}

		public PlayerStats build() {
			return new PlayerStats(
					name,
					id,
					passingAttempts,
					passingCompletions,
					passingYards,
					passingTouchdowns,
					interceptions,
					rushingAttempts,
					rushingYards,
					rushingTouchdowns,
					rushingLong,
					rushingLongTouchdown,
					receptions,
					receivingYards,
					receivingTouchdowns,
					receivingLong,
					receivingLongTouchdown,
					fumbles,
					fumblesLost,
					fumblesRecovered,
					fumbleYards
			);
		}

		public Builder passingAttempts(long passingAttempts) {
			this.passingAttempts = passingAttempts;
			return this;
		}

		public Builder passingCompletions(long passingCompletions) {
			this.passingCompletions = passingCompletions;
			return this;
		}

		public Builder passingYards(long passingYards) {
			this.passingYards = passingYards;
			return this;
		}

		public Builder passingTouchdowns(long passingTouchdowns) {
			this.passingTouchdowns = passingTouchdowns;
			return this;
		}

		public Builder interceptions(long interceptions) {
			this.interceptions = interceptions;
			return this;
		}

		public Builder rushingAttempts(long rushingAttempts) {
			this.rushingAttempts = rushingAttempts;
			return this;
		}

		public Builder rushingYards(long rushingYards) {
			this.rushingYards = rushingYards;
			return this;
		}

		public Builder rushingTouchdowns(long rushingTouchdowns) {
			this.rushingTouchdowns = rushingTouchdowns;
			return this;
		}

		public Builder rushingLong(long rushingLong) {
			this.rushingLong = rushingLong;
			return this;
		}

		public Builder rushingLongTouchdown(long rushingLongTouchdown) {
			this.rushingLongTouchdown = rushingLongTouchdown;
			return this;
		}

		public Builder receptions(long receptions) {
			this.receptions = receptions;
			return this;
		}

		public Builder receivingYards(long receivingYards) {
			this.receivingYards = receivingYards;
			return this;
		}

		public Builder receivingTouchdowns(long receivingTouchdowns) {
			this.receivingTouchdowns = receivingTouchdowns;
			return this;
		}

		public Builder receivingLong(long receivingLong) {
			this.receivingLong = receivingLong;
			return this;
		}

		public Builder receivingLongTouchdown(long receivingLongTouchdown) {
			this.receivingLongTouchdown = receivingLongTouchdown;
			return this;
		}

		public Builder fumbles(long fumbles) {
			this.fumbles = fumbles;
			return this;
		}

		public Builder fumblesLost(long fumblesLost) {
			this.fumblesLost = fumblesLost;
			return this;
		}

		public Builder fumblesRecovered(long fumblesRecovered) {
			this.fumblesRecovered = fumblesRecovered;
			return this;
		}

		public Builder fumbleYards(long fumbleYards) {
			this.fumbleYards = fumbleYards;
			return this;
		}
	}
}
