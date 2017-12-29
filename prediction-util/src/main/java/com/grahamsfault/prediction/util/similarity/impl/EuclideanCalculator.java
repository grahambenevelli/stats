package com.grahamsfault.prediction.util.similarity.impl;

import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class EuclideanCalculator implements CorrelationCalculator {

	@Override
	public <T extends Number> Correlation calculateCorrelation(final List<Node<T>> data1, final List<Node<T>> data2) {
		if (data1.size() != data2.size()) {
			throw new IllegalArgumentException("Node lists need to be the same size");
		}

		Optional<Double> sumOfSquares = IntStream.range(0, data1.size())
				.boxed()
				.map(i -> {
					double d1 = data1.get(i).getValue().doubleValue();
					double d2 = data2.get(i).getValue().doubleValue();
					return Math.pow(d1 - d2, 2);
				})
				.reduce((aDouble, aDouble2) -> aDouble + aDouble2);

		if (!sumOfSquares.isPresent()) {
			return Correlation.of(0);
		}

		return Correlation.of(1/(1+Math.sqrt(sumOfSquares.get())));
	}
}
