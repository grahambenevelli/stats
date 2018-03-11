package com.grahamsfault.stats.server.command.prediction;

import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;

import java.util.function.Function;

import static org.testng.Assert.assertEquals;

public class StatsAssertions {

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

	private final AccuracyStats accuracyStats;

	public static StatsAssertions statsAssertions(AccuracyStats accuracyStats) {
		return new StatsAssertions(accuracyStats);
	}

	private StatsAssertions(AccuracyStats accuracyStats) {
		this.accuracyStats = accuracyStats;
	}

	public StatsAssertions setPassingAttempts(double passingAttempts) {
		this.passingAttempts = passingAttempts;
		return this;
	}

	public StatsAssertions setPassingCompletions(double passingCompletions) {
		this.passingCompletions = passingCompletions;
		return this;
	}

	public StatsAssertions setPassingYards(double passingYards) {
		this.passingYards = passingYards;
		return this;
	}

	public StatsAssertions setPassingTouchdowns(double passingTouchdowns) {
		this.passingTouchdowns = passingTouchdowns;
		return this;
	}

	public StatsAssertions setInterceptions(double interceptions) {
		this.interceptions = interceptions;
		return this;
	}

	public StatsAssertions setRushingAttempts(double rushingAttempts) {
		this.rushingAttempts = rushingAttempts;
		return this;
	}

	public StatsAssertions setRushingYards(double rushingYards) {
		this.rushingYards = rushingYards;
		return this;
	}

	public StatsAssertions setRushingTouchdowns(double rushingTouchdowns) {
		this.rushingTouchdowns = rushingTouchdowns;
		return this;
	}

	public StatsAssertions setRushingLong(double rushingLong) {
		this.rushingLong = rushingLong;
		return this;
	}

	public StatsAssertions setRushingLongTouchdown(double rushingLongTouchdown) {
		this.rushingLongTouchdown = rushingLongTouchdown;
		return this;
	}

	public StatsAssertions setReceptions(double receptions) {
		this.receptions = receptions;
		return this;
	}

	public StatsAssertions setReceivingYards(double receivingYards) {
		this.receivingYards = receivingYards;
		return this;
	}

	public StatsAssertions setReceivingTouchdowns(double receivingTouchdowns) {
		this.receivingTouchdowns = receivingTouchdowns;
		return this;
	}

	public StatsAssertions setReceivingLong(double receivingLong) {
		this.receivingLong = receivingLong;
		return this;
	}

	public StatsAssertions setReceivingLongTouchdown(double receivingLongTouchdown) {
		this.receivingLongTouchdown = receivingLongTouchdown;
		return this;
	}

	public StatsAssertions setFumbles(double fumbles) {
		this.fumbles = fumbles;
		return this;
	}

	public StatsAssertions setFumblesLost(double fumblesLost) {
		this.fumblesLost = fumblesLost;
		return this;
	}

	public StatsAssertions setFumblesRecovered(double fumblesRecovered) {
		this.fumblesRecovered = fumblesRecovered;
		return this;
	}

	public StatsAssertions setFumbleYards(double fumbleYards) {
		this.fumbleYards = fumbleYards;
		return this;
	}

	public void verify() {
		assertEqualStats(AccuracyStats::getPassingAttempts, accuracyStats, passingAttempts);
		assertEqualStats(AccuracyStats::getPassingCompletions, accuracyStats, passingCompletions);
		assertEqualStats(AccuracyStats::getPassingYards, accuracyStats, passingYards);
		assertEqualStats(AccuracyStats::getPassingTouchdowns, accuracyStats, passingTouchdowns);
		assertEqualStats(AccuracyStats::getInterceptions, accuracyStats, interceptions);
		assertEqualStats(AccuracyStats::getRushingAttempts, accuracyStats, rushingAttempts);
		assertEqualStats(AccuracyStats::getRushingYards, accuracyStats, rushingYards);
		assertEqualStats(AccuracyStats::getRushingTouchdowns, accuracyStats, rushingTouchdowns);
		assertEqualStats(AccuracyStats::getRushingLong, accuracyStats, rushingLong);
		assertEqualStats(AccuracyStats::getRushingLongTouchdown, accuracyStats, rushingLongTouchdown);
		assertEqualStats(AccuracyStats::getReceptions, accuracyStats, receptions);
		assertEqualStats(AccuracyStats::getReceivingYards, accuracyStats, receivingYards);
		assertEqualStats(AccuracyStats::getReceivingTouchdowns, accuracyStats, receivingTouchdowns);
		assertEqualStats(AccuracyStats::getReceivingLong, accuracyStats, receivingLong);
		assertEqualStats(AccuracyStats::getReceivingLongTouchdown, accuracyStats, receivingLongTouchdown);
		assertEqualStats(AccuracyStats::getFumbles, accuracyStats, fumbles);
		assertEqualStats(AccuracyStats::getFumblesLost, accuracyStats, fumblesLost);
		assertEqualStats(AccuracyStats::getFumblesRecovered, accuracyStats, fumblesRecovered);
		assertEqualStats(AccuracyStats::getFumbleYards, accuracyStats, fumbleYards);
	}

	private void assertEqualStats(
			Function<AccuracyStats, Double> func,
			AccuracyStats accuracyStats,
			double expected
	) {
		assertEquals(func.apply(accuracyStats), expected);
	}
}