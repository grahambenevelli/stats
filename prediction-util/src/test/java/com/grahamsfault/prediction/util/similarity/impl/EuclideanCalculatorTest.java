package com.grahamsfault.prediction.util.similarity.impl;

import com.google.common.collect.ImmutableMap;
import com.grahamsfault.prediction.util.similarity.Correlation;
import com.grahamsfault.prediction.util.similarity.CorrelationCalculator;
import com.grahamsfault.prediction.util.similarity.TestDataset;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EuclideanCalculatorTest {

	private CorrelationCalculator scoreProvider;

	@BeforeMethod
	public void setup() {
		scoreProvider = new EuclideanCalculator();
	}

	@Test
	public void testTranslatePassingStats() throws Exception {
		Correlation actual = scoreProvider.calculateCorrelation(TestDataset.getDataset(), TestDataset.LISA_ROSE, TestDataset.GENE_SEYMOUR);
		assertEquals(actual, new Correlation(0.29429805508554946));
	}

	@Test
	public void testFirstEntryEmpty() throws Exception {
		Correlation actual = scoreProvider.calculateCorrelation(TestDataset.getDataset(), "No One", TestDataset.GENE_SEYMOUR);
		assertEquals(actual, new Correlation(0));
	}

	@Test
	public void testSecondEntryEmpty() throws Exception {
		Map<String, Map<String, Double>> dataset = TestDataset.getDataset();
		Correlation actual = scoreProvider.calculateCorrelation(dataset, TestDataset.LISA_ROSE, "No One");
		assertEquals(actual, new Correlation(0));
	}

	@Test
	public void testNoMatchingRecords() throws Exception {
		Map<String, Map<String, Double>> dataset = ImmutableMap.<String, Map<String, Double>>builder()
				.put(
						TestDataset.LISA_ROSE,
						ImmutableMap.<String, Double>builder()
								.put(TestDataset.LADY_IN_THE_WATER, 2.5)
								.put(TestDataset.SNAKES_ON_A_PLANE, 3.5)
								.put(TestDataset.JUST_MY_LUCK, 3.0)
								.build()
				)
				.put(
						TestDataset.GENE_SEYMOUR,
						ImmutableMap.<String, Double>builder()
								.put(TestDataset.SUPERMAN_RETURNS, 5.0)
								.put(TestDataset.YOU_ME_AND_DUPREE, 3.5)
								.put(TestDataset.THE_NIGHT_LISTENER, 3.0)
								.build()
				)
				.build();

		Correlation actual = scoreProvider.calculateCorrelation(dataset, TestDataset.LISA_ROSE, TestDataset.GENE_SEYMOUR);
		assertEquals(actual, new Correlation(0));
	}

}