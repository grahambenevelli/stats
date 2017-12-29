package com.grahamsfault.prediction.util.similarity.impl;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.List;
import java.util.Map;

public class PearsonCalculator implements CorrelationCalculator {

	@Override
	public Correlation calculateCorrelation(Map<String, Map<String, Double>> prefs, String person1, String person2) {
		if (!prefs.containsKey(person1) || !prefs.containsKey(person2)) {
			return new Correlation(0);
		}

		ImmutableList.Builder<String> builder = ImmutableList.<String>builder();
		prefs.get(person1).keySet().stream()
				.filter(key -> prefs.get(person2).containsKey(key))
				.forEach(builder::add);

		List<String> similarItems = builder.build();
		if (similarItems.isEmpty()) {
			return new Correlation(0);
		}

		double[] p1array = new double[similarItems.size()];
		double[] p2array = new double[similarItems.size()];
		for (int i = 0; i < similarItems.size(); ++i) {
			String item = similarItems.get(i);
			p1array[i] = prefs.get(person1).get(item);
			p2array[i] = prefs.get(person2).get(item);
		}

		double corr = new PearsonsCorrelation().correlation(p1array, p2array);
		return new Correlation(corr);
	}
}
