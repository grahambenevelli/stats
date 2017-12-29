package com.grahamsfault.prediction.util.similarity.impl;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;

import java.util.List;
import java.util.Map;

public class EuclideanCalculator implements CorrelationCalculator {

	@Override
	public Correlation calculateCorrelation(Map<String, Map<String, Double>> prefs, String person1, String person2) {
		ImmutableList.Builder<String> builder = ImmutableList.<String>builder();
		if (!prefs.containsKey(person1) || !prefs.containsKey(person2)) {
			return new Correlation(0);
		}

		prefs.get(person1).keySet().stream()
				.filter(key -> prefs.get(person2).containsKey(key))
				.forEach(builder::add);

		List<String> similarItems = builder.build();
		if (similarItems.isEmpty()) {
			return new Correlation(0);
		}

		double sumOfSquares = 0;
		for (String item : similarItems) {
			Double score1 = prefs.get(person1).get(item);
			Double score2 = prefs.get(person2).get(item);

			sumOfSquares += Math.pow(score1-score2, 2);
		}

		return new Correlation(1/(1+Math.sqrt(sumOfSquares)));
	}
}
