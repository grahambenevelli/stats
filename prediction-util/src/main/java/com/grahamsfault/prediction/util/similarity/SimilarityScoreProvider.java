package com.grahamsfault.prediction.util.similarity;

import java.util.Map;

public interface SimilarityScoreProvider {

	/**
	 * Return the score from the data and the two entries
	 */
	Score score(Map<String, Map<String, Double>> prefs, String person1, String person2);
}
