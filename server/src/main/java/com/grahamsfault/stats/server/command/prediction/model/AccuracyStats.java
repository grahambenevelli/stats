package com.grahamsfault.stats.server.command.prediction.model;

import com.grahamsfault.stats.server.model.ActualStats;
import com.grahamsfault.stats.server.model.GuessStats;

public class AccuracyStats extends GuessStats {

	public AccuracyStats(
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

	public static AccuracyStats.Builder builder() {
		return new AccuracyStats.Builder();
	}

	public static class Builder {

		// stats
		private int passingAttempts;
		private int passingCompletions;
		private int passingYards;
		private int passingTouchdowns;
		private int interceptions;
		private int rushingAttempts;
		private int rushingYards;
		private int rushingTouchdowns;
		private int rushingLong;
		private int rushingLongTouchdown;
		private int receptions;
		private int receivingYards;
		private int receivingTouchdowns;
		private int receivingLong;
		private int receivingLongTouchdown;
		private int fumbles;
		private int fumblesLost;
		private int fumblesRecovered;
		private int fumbleYards;

		//tracker
		private int playersReviewed;

		private Builder() {
		}

		public AccuracyStats build() {
			if (playersReviewed == 0) {
				return new AccuracyStats(
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0
				);
			}

			return new AccuracyStats(
					1.0 * passingAttempts / playersReviewed,
					1.0 * passingCompletions / playersReviewed,
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

		public Builder incrementPlayersReviewed(long playersReviewed) {
			this.playersReviewed += playersReviewed;
			return this;
		}

		public Builder incrementPlayersRecieved() {
			this.playersReviewed++;
			return this;
		}

		private double value(long playerStat, double predictionStat) {
			return Math.abs(playerStat - predictionStat);
		}

		public Builder incrementPassingAttempts(ActualStats playerStats, GuessStats predictionStats) {
			this.passingAttempts += value(playerStats.getPassingAttempts(), predictionStats.getPassingAttempts());
			return this;
		}

		public Builder incrementPassingCompletions(ActualStats playerStats, GuessStats predictionStats) {
			this.passingCompletions += value(playerStats.getPassingCompletions(), predictionStats.getPassingCompletions());
			return this;
		}

		public Builder incrementPassingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.passingYards += value(playerStats.getPassingYards(), predictionStats.getPassingYards());
			return this;
		}

		public Builder incrementPassingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.passingTouchdowns += value(playerStats.getPassingTouchdowns(), predictionStats.getPassingTouchdowns());
			return this;
		}

		public Builder incrementInterceptions(ActualStats playerStats, GuessStats predictionStats) {
			this.interceptions += value(playerStats.getInterceptions(), predictionStats.getInterceptions());
			return this;
		}

		public Builder incrementRushingAttempts(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingAttempts += value(playerStats.getRushingAttempts(), predictionStats.getRushingAttempts());
			return this;
		}

		public Builder incrementRushingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingYards += value(playerStats.getRushingYards(), predictionStats.getRushingYards());
			return this;
		}

		public Builder incrementRushingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingTouchdowns += value(playerStats.getRushingTouchdowns(), predictionStats.getRushingTouchdowns());
			return this;
		}

		public Builder incrementRushingLong(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingLong += value(playerStats.getRushingLong(), predictionStats.getRushingLong());
			return this;
		}

		public Builder incrementRushingLongTouchdown(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingLongTouchdown += value(playerStats.getRushingLongTouchdown(), predictionStats.getRushingLongTouchdown());
			return this;
		}

		public Builder incrementReceptions(ActualStats playerStats, GuessStats predictionStats) {
			this.receptions += value(playerStats.getReceptions(), predictionStats.getReceptions());
			return this;
		}

		public Builder incrementReceivingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingYards += value(playerStats.getReceivingYards(), predictionStats.getReceivingYards());
			return this;
		}

		public Builder incrementReceivingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingTouchdowns += value(playerStats.getReceivingTouchdowns(), predictionStats.getReceivingTouchdowns());
			return this;
		}

		public Builder incrementReceivingLong(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingLong += value(playerStats.getReceivingLong(), predictionStats.getReceivingLong());
			return this;
		}

		public Builder incrementReceivingLongTouchdown(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingLongTouchdown += value(playerStats.getReceivingLongTouchdown(), predictionStats.getReceivingLongTouchdown());
			return this;
		}

		public Builder incrementFumbles(ActualStats playerStats, GuessStats predictionStats) {
			this.fumbles += value(playerStats.getFumbles(), predictionStats.getFumbles());
			return this;
		}

		public Builder incrementFumblesLost(ActualStats playerStats, GuessStats predictionStats) {
			this.fumblesLost += value(playerStats.getFumblesLost(), predictionStats.getFumblesLost());
			return this;
		}

		public Builder incrementFumblesRecovered(ActualStats playerStats, GuessStats predictionStats) {
			this.fumblesRecovered += value(playerStats.getFumblesRecovered(), predictionStats.getFumblesRecovered());
			return this;
		}

		public Builder incrementFumbleYards(ActualStats playerStats, GuessStats predictionStats) {
			this.fumbleYards += value(playerStats.getFumbleYards(), predictionStats.getFumbleYards());
			return this;
		}
	}
}
