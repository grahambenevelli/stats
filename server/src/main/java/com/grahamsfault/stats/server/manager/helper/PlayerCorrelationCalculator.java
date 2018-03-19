package com.grahamsfault.stats.server.manager.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.stats.server.command.prediction.model.NormalizedStats;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerCorrelationCalculator {

	private final CorrelationCalculator correlationCalculator;
	private final List<Function<NormalizedStats, Double>> correlationStats;
	private final String description;

	public PlayerCorrelationCalculator(CorrelationCalculator correlationCalculator) {
		this(
				correlationCalculator,
				buildDefaultCorrelationStats(),
				buildDefaultDescription()
		);
	}

	private PlayerCorrelationCalculator(CorrelationCalculator correlationCalculator, List<Function<NormalizedStats, Double>> correlationStats, String description) {
		this.correlationCalculator = correlationCalculator;
		this.correlationStats = correlationStats;
		this.description = description;
	}

	public double calculateCorrelation(NormalizedStats otherStats, NormalizedStats playerStats) {
		List<Node<Double>> normalizedNodes = getNodeList(otherStats);
		List<Node<Double>> playerNodes = getNodeList(playerStats);

		return correlationCalculator.calculateCorrelation(normalizedNodes, playerNodes).getValue();
	}

	private List<Node<Double>> getNodeList(NormalizedStats stats) {
		return this.correlationStats.stream()
				.map(func -> Node.of(func.apply(stats)))
				.collect(Collectors.toList());
	}

	public String getName() {
		return correlationCalculator.getClass().getSimpleName();
	}

	public String getDescription() {
		return description;
	}

	private static ImmutableList<Function<NormalizedStats, Double>> buildDefaultCorrelationStats() {
		return ImmutableList.<Function<NormalizedStats, Double>>builder()
				.add(NormalizedStats::getPassingAttempts)
				.add(NormalizedStats::getPassingCompletions)
				.add(NormalizedStats::getPassingYards)
				.add(NormalizedStats::getPassingTouchdowns)
				.add(NormalizedStats::getInterceptions)
				.add(NormalizedStats::getRushingAttempts)
				.add(NormalizedStats::getRushingYards)
				.add(NormalizedStats::getRushingTouchdowns)
				.add(NormalizedStats::getRushingLong)
				.add(NormalizedStats::getRushingLongTouchdown)
				.add(NormalizedStats::getReceptions)
				.add(NormalizedStats::getReceivingYards)
				.add(NormalizedStats::getReceivingTouchdowns)
				.add(NormalizedStats::getReceivingLong)
				.add(NormalizedStats::getReceivingLongTouchdown)
				.add(NormalizedStats::getFumbles)
				.add(NormalizedStats::getFumblesLost)
				.add(NormalizedStats::getFumblesRecovered)
				.add(NormalizedStats::getFumbleYards)
				.build();
	}

	private static String buildDefaultDescription() {
		try {
			return new ObjectMapper().writeValueAsString(ImmutableList.<String>builder()
					.add("NormalizedStats::getPassingAttempts")
					.add("NormalizedStats::getPassingCompletions")
					.add("NormalizedStats::getPassingYards")
					.add("NormalizedStats::getPassingTouchdowns")
					.add("NormalizedStats::getInterceptions")
					.add("NormalizedStats::getRushingAttempts")
					.add("NormalizedStats::getRushingYards")
					.add("NormalizedStats::getRushingTouchdowns")
					.add("NormalizedStats::getRushingLong")
					.add("NormalizedStats::getRushingLongTouchdown")
					.add("NormalizedStats::getReceptions")
					.add("NormalizedStats::getReceivingYards")
					.add("NormalizedStats::getReceivingTouchdowns")
					.add("NormalizedStats::getReceivingLong")
					.add("NormalizedStats::getReceivingLongTouchdown")
					.add("NormalizedStats::getFumbles")
					.add("NormalizedStats::getFumblesLost")
					.add("NormalizedStats::getFumblesRecovered")
					.add("NormalizedStats::getFumbleYards")
					.build());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}