package com.grahamsfault.prediction.util.similarity.impl;

import com.grahamsfault.prediction.util.Node;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.List;

public class PearsonCalculator implements CorrelationCalculator {

	@Override
	public <T extends Number> Correlation calculateCorrelation(List<Node<T>> data1, List<Node<T>> data2) {
		if (data1.size() != data2.size()) {
			throw new IllegalArgumentException("Node lists need to be the same size");
		}

		double[] p1array = new double[data1.size()];
		double[] p2array = new double[data2.size()];
		for (int i = 0; i < data1.size(); ++i) {
			p1array[i] = data1.get(i).getValue().doubleValue();
			p2array[i] = data2.get(i).getValue().doubleValue();
		}

		double corr = new PearsonsCorrelation().correlation(p1array, p2array);
		return Correlation.of(corr);
	}
}
