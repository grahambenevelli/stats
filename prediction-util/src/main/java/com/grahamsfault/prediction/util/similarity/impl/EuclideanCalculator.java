package com.grahamsfault.prediction.util.similarity.impl;

import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.prediction.util.Node;

import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class EuclideanCalculator implements CorrelationCalculator {

	@Override
	public <T extends Number> Correlation calculateCorrelation(List<Node<T>> data1, List<Node<T>> data2) {
		if (data1.size() != data2.size()) {
			throw new IllegalArgumentException("Node lists need to be the same size");
		}

		double sumOfSquares = 0;
		for(int i = 0; i < data1.size(); ++i) {
			double d1 = data1.get(i).getValue().doubleValue();
			double d2 = data2.get(i).getValue().doubleValue();
			sumOfSquares += Math.pow(d1 - d2, 2);
		}

		return Correlation.of(1/(1+Math.sqrt(sumOfSquares)));
	}
}
