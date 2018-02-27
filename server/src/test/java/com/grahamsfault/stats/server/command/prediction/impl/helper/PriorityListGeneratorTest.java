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
						ImmutableList.<Integer>builder()
								.build(),
						ImmutableList.<Integer>builder()
								.build(),
				},
				{
						ImmutableList.<Integer>builder()
								.add(1)
								.build(),
						ImmutableList.<Integer>builder()
								.add(1)
								.build(),
				},
				{
						ImmutableList.<Integer>builder()
								.add(2)
								.add(1)
								.build(),
						ImmutableList.<Integer>builder()
								.add(2)
								.add(1)
								.build(),
				},
				{
						ImmutableList.<Integer>builder()
								.add(1)
								.add(2)
								.build(),
						ImmutableList.<Integer>builder()
								.add(2)
								.add(1)
								.build(),
				},
				{
						ImmutableList.<Integer>builder()
								.add(1)
								.add(2)
								.add(3)
								.add(4)
								.add(5)
								.add(6)
								.build(),
						ImmutableList.<Integer>builder()
								.add(6)
								.add(5)
								.add(4)
								.add(3)
								.add(2)
								.build(),
				},
				{
						ImmutableList.<Integer>builder()
								.add(3)
								.add(6)
								.add(4)
								.add(5)
								.add(2)
								.add(1)
								.build(),
						ImmutableList.<Integer>builder()
								.add(6)
								.add(5)
								.add(4)
								.add(3)
								.add(2)
								.build(),
				}
		};

	}

	@Test(dataProvider = "items")
	public void testGetPlayerById(List<Integer> items, List<IntStream> expected) {
		PriorityListGenerator<Integer> listGenerator = new PriorityListGenerator<>(5);
		items.forEach((item) -> listGenerator.add(item.doubleValue(), item));

		assertEquals(listGenerator.build(), expected);
	}
}