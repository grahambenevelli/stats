package com.grahamsfault.nfl.stats.command.prediction.model;

import com.grahamsfault.nfl.stats.model.ActualStats;
import com.grahamsfault.nfl.stats.model.GuessStats;

public class AverageStats extends GuessStats {

	public AverageStats(
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

	public static AverageStats.Builder builder() {
		return new AverageStats.Builder();
	}

	public static class Builder {

		// stats
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

		//tracker
		private long playersReviewed;

		private Builder() {
		}

		public AverageStats build() {
			return new AverageStats(
					1.0 * passingAttempts / playersReviewed,
					1.0 * passingCompletions  / playersReviewed,
					1.0 * passingYards / playersReviewed,
					1.0 * passingTouchdowns / playersReviewed,
					1.0 * interceptions / playersReviewed,
					1.0 * rushingAttempts / playersReviewed,
					1.0 * rushingYards / playersReviewed,
					1.0 * rushingTouchdowns / playersReviewed,
					1.0 * rushingLong / playersReviewed,
					1.0 * rushingLongTouchdown / playersReviewed,
					1.0 * receptions / playersReviewed,
					1.0 * receivingYards / playersReviewed,
					1.0 * receivingTouchdowns / playersReviewed,
					1.0 * receivingLong / playersReviewed,
					1.0 * receivingLongTouchdown / playersReviewed,
					1.0 * fumbles / playersReviewed,
					1.0 * fumblesLost / playersReviewed,
					1.0 * fumblesRecovered / playersReviewed,
					1.0 * fumbleYards / playersReviewed
			);
		}

		public Builder incrementPassingAttempts(long passingAttempts) {
			this.passingAttempts += passingAttempts;
			return this;
		}

		public Builder incrementPassingCompletions(long passingCompletions) {
			this.passingCompletions += passingCompletions;
			return this;
		}

		public Builder incrementPassingYards(long passingYards) {
			this.passingYards += passingYards;
			return this;
		}

		public Builder incrementPassingTouchdowns(long passingTouchdowns) {
			this.passingTouchdowns += passingTouchdowns;
			return this;
		}

		public Builder incrementInterceptions(long interceptions) {
			this.interceptions += interceptions;
			return this;
		}

		public Builder incrementRushingAttempts(long rushingAttempts) {
			this.rushingAttempts += rushingAttempts;
			return this;
		}

		public Builder incrementRushingYards(long rushingYards) {
			this.rushingYards += rushingYards;
			return this;
		}

		public Builder incrementRushingTouchdowns(long rushingTouchdowns) {
			this.rushingTouchdowns += rushingTouchdowns;
			return this;
		}

		public Builder incrementRushingLong(long rushingLong) {
			this.rushingLong += rushingLong;
			return this;
		}

		public Builder incrementRushingLongTouchdown(long rushingLongTouchdown) {
			this.rushingLongTouchdown += rushingLongTouchdown;
			return this;
		}

		public Builder incrementReceptions(long receptions) {
			this.receptions += receptions;
			return this;
		}

		public Builder incrementReceivingYards(long receivingYards) {
			this.receivingYards += receivingYards;
			return this;
		}

		public Builder incrementReceivingTouchdowns(long receivingTouchdowns) {
			this.receivingTouchdowns += receivingTouchdowns;
			return this;
		}

		public Builder incrementReceivingLong(long receivingLong) {
			this.receivingLong += receivingLong;
			return this;
		}

		public Builder incrementReceivingLongTouchdown(long receivingLongTouchdown) {
			this.receivingLongTouchdown += receivingLongTouchdown;
			return this;
		}

		public Builder incrementFumbles(long fumbles) {
			this.fumbles += fumbles;
			return this;
		}

		public Builder incrementFumblesLost(long fumblesLost) {
			this.fumblesLost += fumblesLost;
			return this;
		}

		public Builder incrementFumblesRecovered(long fumblesRecovered) {
			this.fumblesRecovered += fumblesRecovered;
			return this;
		}

		public Builder incrementFumbleYards(long fumbleYards) {
			this.fumbleYards += fumbleYards;
			return this;
		}

		public Builder incrementPlayersRecieved() {
			this.playersReviewed++;
			return this;
		}

		public Builder incrementStats(ActualStats playerStats) {
			incrementPassingAttempts(playerStats.getPassingAttempts());
			incrementPassingCompletions(playerStats.getPassingCompletions());
			incrementPassingYards(playerStats.getPassingYards());
			incrementPassingTouchdowns(playerStats.getPassingTouchdowns());
			incrementInterceptions(playerStats.getInterceptions());
			incrementRushingAttempts(playerStats.getRushingAttempts());
			incrementRushingYards(playerStats.getRushingYards());
			incrementRushingTouchdowns(playerStats.getRushingTouchdowns());
			incrementRushingLong(playerStats.getRushingLong());
			incrementRushingLongTouchdown(playerStats.getRushingLongTouchdown());
			incrementReceptions(playerStats.getReceptions());
			incrementReceivingYards(playerStats.getReceivingYards());
			incrementReceivingTouchdowns(playerStats.getReceivingTouchdowns());
			incrementReceivingLong(playerStats.getReceivingLong());
			incrementReceivingLongTouchdown(playerStats.getReceivingLongTouchdown());
			incrementFumbles(playerStats.getFumbles());
			incrementFumblesLost(playerStats.getFumblesLost());
			incrementFumblesRecovered(playerStats.getFumblesRecovered());
			incrementFumbleYards(playerStats.getFumbleYards());

			return this;
		}
	}
}
