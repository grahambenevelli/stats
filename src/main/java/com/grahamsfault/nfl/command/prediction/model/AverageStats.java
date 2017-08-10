package com.grahamsfault.nfl.command.prediction.model;

import com.grahamsfault.nfl.model.GuessStats;

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

		public void incrementPassingAttempts(long passingAttempts) {
			this.passingAttempts += passingAttempts;
		}

		public void incrementPassingCompletions(long passingCompletions) {
			this.passingCompletions += passingCompletions;
		}

		public void incrementPassingYards(long passingYards) {
			this.passingYards += passingYards;
		}

		public void incrementPassingTouchdowns(long passingTouchdowns) {
			this.passingTouchdowns += passingTouchdowns;
		}

		public void incrementInterceptions(long interceptions) {
			this.interceptions += interceptions;
		}

		public void incrementRushingAttempts(long rushingAttempts) {
			this.rushingAttempts += rushingAttempts;
		}

		public void incrementRushingYards(long rushingYards) {
			this.rushingYards += rushingYards;
		}

		public void incrementRushingTouchdowns(long rushingTouchdowns) {
			this.rushingTouchdowns += rushingTouchdowns;
		}

		public void incrementRushingLong(long rushingLong) {
			this.rushingLong += rushingLong;
		}

		public void incrementRushingLongTouchdown(long rushingLongTouchdown) {
			this.rushingLongTouchdown += rushingLongTouchdown;
		}

		public void incrementReceptions(long receptions) {
			this.receptions += receptions;
		}

		public void incrementReceivingYards(long receivingYards) {
			this.receivingYards += receivingYards;
		}

		public void incrementReceivingTouchdowns(long receivingTouchdowns) {
			this.receivingTouchdowns += receivingTouchdowns;
		}

		public void incrementReceivingLong(long receivingLong) {
			this.receivingLong += receivingLong;
		}

		public void incrementReceivingLongTouchdown(long receivingLongTouchdown) {
			this.receivingLongTouchdown += receivingLongTouchdown;
		}

		public void incrementFumbles(long fumbles) {
			this.fumbles += fumbles;
		}

		public void incrementFumblesLost(long fumblesLost) {
			this.fumblesLost += fumblesLost;
		}

		public void incrementFumblesRecovered(long fumblesRecovered) {
			this.fumblesRecovered += fumblesRecovered;
		}

		public void incrementFumbleYards(long fumbleYards) {
			this.fumbleYards += fumbleYards;
		}

		public void incrementPlayersReviewed(long playersReviewed) {
			this.playersReviewed += playersReviewed;
		}

		public void incrementPlayersRecieved() {
			this.playersReviewed++;
		}
	}
}
