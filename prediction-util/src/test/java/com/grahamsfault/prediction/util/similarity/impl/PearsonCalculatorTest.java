package com.grahamsfault.prediction.util.similarity.impl;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.prediction.util.Node;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class PearsonCalculatorTest {

	private CorrelationCalculator correlationCalculator;

	@BeforeMethod
	public void setup() {
		correlationCalculator = new PearsonCalculator();
	}

	@Test
	public void testNodeListCorrelation() throws Exception {
		ImmutableList<Node<Double>> data1 = ImmutableList.<Node<Double>>builder()
				.add(Node.of(2.5))
				.add(Node.of(3.5))
				.add(Node.of(3.0))
				.add(Node.of(3.5))
				.add(Node.of(2.5))
				.add(Node.of(3.0))
				.build();

		ImmutableList<Node<Double>> data2 = ImmutableList.<Node<Double>>builder()
				.add(Node.of(3.0))
				.add(Node.of(3.5))
				.add(Node.of(1.5))
				.add(Node.of(5.0))
				.add(Node.of(3.5))
				.add(Node.of(3.0))
				.build();

		Correlation actual = correlationCalculator.calculateCorrelation(data1, data2);
		assertEquals(actual, Correlation.of(0.3960590171906697));
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testNodeListCorrelationListSizeDoesNotMatch() throws Exception {
		ImmutableList<Node<Double>> data1 = ImmutableList.<Node<Double>>builder()
				.add(Node.of(2.5))
				.add(Node.of(3.5))
				.add(Node.of(3.0))
				.add(Node.of(3.5))
				.add(Node.of(2.5))
				.add(Node.of(3.0))
				.build();

		ImmutableList<Node<Double>> data2 = ImmutableList.<Node<Double>>builder()
				.add(Node.of(3.0))
				.build();

		correlationCalculator.calculateCorrelation(data1, data2);
	}

}