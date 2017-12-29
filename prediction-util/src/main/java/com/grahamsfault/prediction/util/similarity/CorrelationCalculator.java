package com.grahamsfault.prediction.util.similarity;

import com.grahamsfault.prediction.util.Node;

import java.util.List;

public interface CorrelationCalculator {

	/**
	 * Return the calculateCorrelation from the data and the two entries
	 */
	<T extends Number> Correlation calculateCorrelation(List<Node<T>> data1, List<Node<T>> data2);
}
