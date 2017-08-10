package com.grahamsfault.nfl.command.prediction.model;

import com.grahamsfault.nfl.model.ActualStats;
import com.grahamsfault.nfl.model.GuessStats;

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
					1.0 * fumbleYards / playersReviewed);
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

		private double value(long playerStat, double predictionStat) {
			return Math.abs(playerStat - predictionStat);
		}

		public void incrementPassingAttempts(ActualStats playerStats, GuessStats predictionStats) {
			this.passingAttempts += value(playerStats.getPassingAttempts(), predictionStats.getPassingAttempts());
		}

		public void incrementPassingCompletions(ActualStats playerStats, GuessStats predictionStats) {
			this.passingCompletions += value(playerStats.getPassingCompletions(), predictionStats.getPassingCompletions());
		}

		public void incrementPassingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.passingYards += value(playerStats.getPassingYards(), predictionStats.getPassingYards());
		}

		public void incrementPassingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.passingTouchdowns += value(playerStats.getPassingTouchdowns(), predictionStats.getPassingTouchdowns());
		}

		public void incrementInterceptions(ActualStats playerStats, GuessStats predictionStats) {
			this.interceptions += value(playerStats.getInterceptions(), predictionStats.getInterceptions());
		}

		public void incrementRushingAttempts(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingAttempts += value(playerStats.getRushingAttempts(), predictionStats.getRushingAttempts());
		}

		public void incrementRushingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingYards += value(playerStats.getRushingYards(), predictionStats.getRushingYards());
		}

		public void incrementRushingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingTouchdowns += value(playerStats.getRushingTouchdowns(), predictionStats.getRushingTouchdowns());
		}

		public void incrementRushingLong(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingLong += value(playerStats.getRushingLong(), predictionStats.getRushingLong());
		}

		public void incrementRushingLongTouchdown(ActualStats playerStats, GuessStats predictionStats) {
			this.rushingLongTouchdown += value(playerStats.getRushingLongTouchdown(), predictionStats.getRushingLongTouchdown());
		}

		public void incrementReceptions(ActualStats playerStats, GuessStats predictionStats) {
			this.receptions += value(playerStats.getReceptions(), predictionStats.getReceptions());
		}

		public void incrementReceivingYards(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingYards += value(playerStats.getReceivingYards(), predictionStats.getReceivingYards());
		}

		public void incrementReceivingTouchdowns(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingTouchdowns += value(playerStats.getReceivingTouchdowns(), predictionStats.getReceivingTouchdowns());
		}

		public void incrementReceivingLong(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingLong += value(playerStats.getReceivingLong(), predictionStats.getReceivingLong());
		}

		public void incrementReceivingLongTouchdown(ActualStats playerStats, GuessStats predictionStats) {
			this.receivingLongTouchdown += value(playerStats.getReceivingLongTouchdown(), predictionStats.getReceivingLongTouchdown());
		}

		public void incrementFumbles(ActualStats playerStats, GuessStats predictionStats) {
			this.fumbles += value(playerStats.getFumbles(), predictionStats.getFumbles());
		}

		public void incrementFumblesLost(ActualStats playerStats, GuessStats predictionStats) {
			this.fumblesLost += value(playerStats.getFumblesLost(), predictionStats.getFumblesLost());
		}

		public void incrementFumblesRecovered(ActualStats playerStats, GuessStats predictionStats) {
			this.fumblesRecovered += value(playerStats.getFumblesRecovered(), predictionStats.getFumblesRecovered());
		}

		public void incrementFumbleYards(ActualStats playerStats, GuessStats predictionStats) {
			this.fumbleYards += value(playerStats.getFumbleYards(), predictionStats.getFumbleYards());
		}
	}
}
