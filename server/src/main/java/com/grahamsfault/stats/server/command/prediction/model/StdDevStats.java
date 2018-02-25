package com.grahamsfault.stats.server.command.prediction.model;

import com.grahamsfault.stats.server.model.GuessStats;

public class StdDevStats extends GuessStats {

	public StdDevStats(
			Double passingAttempts,
			Double passingCompletions,
			Double passingYards,
			Double passingTouchdowns,
			Double interceptions,
			Double rushingAttempts,
			Double rushingYards,
			Double rushingTouchdowns,
			Double rushingLong,
			Double rushingLongTouchdown,
			Double receptions,
			Double receivingYards,
			Double receivingTouchdowns,
			Double receivingLong,
			Double receivingLongTouchdown,
			Double fumbles,
			Double fumblesLost,
			Double fumblesRecovered,
			Double fumbleYards
	) {
		super(
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

	public static StdDevStats.Builder builder() {
		return new StdDevStats.Builder();
	}

	public static class Builder {

		private double passingAttempts;
		private double passingCompletions;
		private double passingYards;
		private double passingTouchdowns;
		private double interceptions;
		private double rushingAttempts;
		private double rushingYards;
		private double rushingTouchdowns;
		private double rushingLong;
		private double rushingLongTouchdown;
		private double receptions;
		private double receivingYards;
		private double receivingTouchdowns;
		private double receivingLong;
		private double receivingLongTouchdown;
		private double fumbles;
		private double fumblesLost;
		private double fumblesRecovered;
		private double fumbleYards;

		public StdDevStats build() {
			return new StdDevStats(
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

		public Builder passingAttempts(double passingAttempts) {
			this.passingAttempts = passingAttempts;
			return this;
		}

		public Builder passingCompletions(double passingCompletions) {
			this.passingCompletions = passingCompletions;
			return this;
		}

		public Builder passingYards(double passingYards) {
			this.passingYards = passingYards;
			return this;
		}

		public Builder passingTouchdowns(double passingTouchdowns) {
			this.passingTouchdowns = passingTouchdowns;
			return this;
		}

		public Builder interceptions(double interceptions) {
			this.interceptions = interceptions;
			return this;
		}

		public Builder rushingAttempts(double rushingAttempts) {
			this.rushingAttempts = rushingAttempts;
			return this;
		}

		public Builder rushingYards(double rushingYards) {
			this.rushingYards = rushingYards;
			return this;
		}

		public Builder rushingTouchdowns(double rushingTouchdowns) {
			this.rushingTouchdowns = rushingTouchdowns;
			return this;
		}

		public Builder rushingLong(double rushingLong) {
			this.rushingLong = rushingLong;
			return this;
		}

		public Builder rushingLongTouchdown(double rushingLongTouchdown) {
			this.rushingLongTouchdown = rushingLongTouchdown;
			return this;
		}

		public Builder receptions(double receptions) {
			this.receptions = receptions;
			return this;
		}

		public Builder receivingYards(double receivingYards) {
			this.receivingYards = receivingYards;
			return this;
		}

		public Builder receivingTouchdowns(double receivingTouchdowns) {
			this.receivingTouchdowns = receivingTouchdowns;
			return this;
		}

		public Builder receivingLong(double receivingLong) {
			this.receivingLong = receivingLong;
			return this;
		}

		public Builder receivingLongTouchdown(double receivingLongTouchdown) {
			this.receivingLongTouchdown = receivingLongTouchdown;
			return this;
		}

		public Builder fumbles(double fumbles) {
			this.fumbles = fumbles;
			return this;
		}

		public Builder fumblesLost(double fumblesLost) {
			this.fumblesLost = fumblesLost;
			return this;
		}

		public Builder fumblesRecovered(double fumblesRecovered) {
			this.fumblesRecovered = fumblesRecovered;
			return this;
		}

		public Builder fumbleYards(double fumbleYards) {
			this.fumbleYards = fumbleYards;
			return this;
		}
	}
}
