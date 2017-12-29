package com.grahamsfault.prediction.util.similarity;

import java.util.Map;

public interface CorrelationCalculator {

	/**
	 * Return the calculateCorrelation from the data and the two entries
	 */
	Correlation calculateCorrelation(Map<String, Map<String, Double>> prefs, String person1, String person2);
}
