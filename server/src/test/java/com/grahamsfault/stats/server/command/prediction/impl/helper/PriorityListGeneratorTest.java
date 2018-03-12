package com.grahamsfault.stats.server.command.prediction.impl.helper;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PriorityListGeneratorTest {

	@BeforeClass
	public void setup() throws MalformedURLException, SQLException {

	}

	@DataProvider(name = "items")
	public static Object[][] credentials() {
		return new Object[][] {
				{
						ImmutableList.<Double>builder()
								.build(),
						ImmutableList.<Double>builder()
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(1.0)
								.build(),
						ImmutableList.<Double>builder()
								.add(1.0)
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(2.0)
								.add(1.0)
								.build(),
						ImmutableList.<Double>builder()
								.add(2.0)
								.add(1.0)
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(1.0)
								.add(2.0)
								.build(),
						ImmutableList.<Double>builder()
								.add(2.0)
								.add(1.0)
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(1.0)
								.add(2.0)
								.add(3.0)
								.add(4.0)
								.add(5.0)
								.add(6.0)
								.build(),
						ImmutableList.<Double>builder()
								.add(6.0)
								.add(5.0)
								.add(4.0)
								.add(3.0)
								.add(2.0)
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(3.0)
								.add(6.0)
								.add(4.0)
								.add(5.0)
								.add(2.0)
								.add(1.0)
								.build(),
						ImmutableList.<Double>builder()
								.add(6.0)
								.add(5.0)
								.add(4.0)
								.add(3.0)
								.add(2.0)
								.build(),
				},
				{
						ImmutableList.<Double>builder()
								.add(0.3)
								.add(0.6)
								.add(0.4)
								.add(0.5)
								.add(0.2)
								.add(0.1)
								.build(),
						ImmutableList.<Double>builder()
								.add(0.6)
								.add(0.5)
								.add(0.4)
								.add(0.3)
								.add(0.2)
								.build(),
				}
		};

	}

	@Test(dataProvider = "items")
	public void testGetPlayerById(List<Double> items, List<IntStream> expected) {
		PriorityListGenerator<Double> listGenerator = new PriorityListGenerator<>(5);
		items.forEach((item) -> listGenerator.add(item.doubleValue(), item));

		assertEquals(listGenerator.build(), expected);
	}
}