package com.grahamsfault.stats.server.command.prediction;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.grahamsfault.stats.server.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.model.ActualStats;
import com.grahamsfault.stats.server.model.GuessStats;

import java.util.Map;

/**
 * The prediction results, showing the average inaccuracy
 */
public class PredictionResults {

	private final AccuracyStats qbStats;
	private final AccuracyStats rbStats;
	private final AccuracyStats wrStats;
	private final AccuracyStats teStats;

	private PredictionResults(AccuracyStats qbStats, AccuracyStats rbStats, AccuracyStats wrStats, AccuracyStats teStats) {
		this.qbStats = qbStats;
		this.rbStats = rbStats;
		this.wrStats = wrStats;
		this.teStats = teStats;
	}

	public AccuracyStats getQbStats() {
		return qbStats;
	}

	public AccuracyStats getRbStats() {
		return rbStats;
	}

	public AccuracyStats getWrStats() {
		return wrStats;
	}

	public AccuracyStats getTeStats() {
		return teStats;
	}

	/**
	 * Get stats for the given position
	 *
	 * @param position The position we want stats for
	 * @return The accuracy stats
	 */
	public AccuracyStats getStats(Position position) {
		Map<Position, Supplier<AccuracyStats>> map = ImmutableMap.<Position, Supplier<AccuracyStats>>builder()
				.put(Position.QB, this::getQbStats)
				.put(Position.RB, this::getRbStats)
				.put(Position.WR, this::getWrStats)
				.put(Position.TE, this::getTeStats)
				.build();

		 return map.get(position).get();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final Map<Position, AccuracyStats.Builder> builders;

		private Builder() {
			builders = ImmutableMap.<Position, AccuracyStats.Builder>builder()
					.put(Position.QB, AccuracyStats.builder())
					.put(Position.RB, AccuracyStats.builder())
					.put(Position.WR, AccuracyStats.builder())
					.put(Position.TE, AccuracyStats.builder())
					.build();
		}

		private void incrementIncrementalStats(AccuracyStats.Builder builder, ActualStats playerStats, GuessStats predictionStats) {
			builder.incrementPlayersRecieved();

			builder.incrementPassingAttempts(playerStats, predictionStats);
			builder.incrementPassingCompletions(playerStats, predictionStats);
			builder.incrementPassingYards(playerStats, predictionStats);
			builder.incrementPassingTouchdowns(playerStats, predictionStats);
			builder.incrementInterceptions(playerStats, predictionStats);
			builder.incrementRushingAttempts(playerStats, predictionStats);
			builder.incrementRushingYards(playerStats, predictionStats);
			builder.incrementRushingTouchdowns(playerStats, predictionStats);
			builder.incrementRushingLong(playerStats, predictionStats);
			builder.incrementRushingLongTouchdown(playerStats, predictionStats);
			builder.incrementReceptions(playerStats, predictionStats);
			builder.incrementReceivingYards(playerStats, predictionStats);
			builder.incrementReceivingTouchdowns(playerStats, predictionStats);
			builder.incrementReceivingLong(playerStats, predictionStats);
			builder.incrementReceivingLongTouchdown(playerStats, predictionStats);
			builder.incrementFumbles(playerStats, predictionStats);
			builder.incrementFumblesLost(playerStats, predictionStats);
			builder.incrementFumblesRecovered(playerStats, predictionStats);
			builder.incrementFumbleYards(playerStats, predictionStats);
		}

		public void increment(Position position, ActualStats playerStats, GuessStats predictionStats) {
			incrementIncrementalStats(builders.get(position), playerStats, predictionStats);
		}

		public PredictionResults build() {
			return new PredictionResults(
					this.builders.get(Position.QB).build(),
					this.builders.get(Position.RB).build(),
					this.builders.get(Position.WR).build(),
					this.builders.get(Position.TE).build()
			);
		}
	}
}
