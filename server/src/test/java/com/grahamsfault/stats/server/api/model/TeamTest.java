package com.grahamsfault.stats.server.api.model;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TeamTest {

	private static class TestData {

		public final String abbreviation;
		public final Team expected;

		private TestData(String abbreviation, Team expected) {
			this.abbreviation = abbreviation;
			this.expected = expected;
		}
	}

	@DataProvider
	public static Object[][] getForValueProvider() throws Exception {

		return new Object[][]{
				{new TestData("ARI", Team.ARIZONA)},
				{new TestData("ATL", Team.ATLANTA)},
				{new TestData("BAL", Team.BALTIMORE)},
				{new TestData("BUF", Team.BUFFALO)},
				{new TestData("CAR", Team.CAROLINA)},
				{new TestData("CHI", Team.CHICAGO)},
				{new TestData("CIN", Team.CINCINNATI)},
				{new TestData("CLE", Team.CLEVELAND)},
				{new TestData("DAL", Team.DALLAS)},
				{new TestData("DEN", Team.DENVER)},
				{new TestData("DET", Team.DETROIT)},
				{new TestData("GB", Team.GREEN_BAY)},
				{new TestData("HOU", Team.HOUSTON)},
				{new TestData("IND", Team.INDIANAPOLIS)},
				{new TestData("JAC", Team.JACKSONVILLE)},
				{new TestData("KC", Team.KANSAS_CITY)},
				{new TestData("LAR", Team.LOS_ANGELES_RAMS)},
				{new TestData("STL", Team.LOS_ANGELES_RAMS)},
				{new TestData("SD", Team.LOS_ANGELES_CHARGERS)},
				{new TestData("LAC", Team.LOS_ANGELES_CHARGERS)},
				{new TestData("MIA", Team.MIAMI)},
				{new TestData("MIN", Team.MINNESOTA)},
				{new TestData("NE", Team.NEW_ENGLAND)},
				{new TestData("NO", Team.NEW_ORLEANS)},
				{new TestData("NYG", Team.NEW_YORK_GIANTS)},
				{new TestData("NYJ", Team.NEW_YORK_JETS)},
				{new TestData("OAK", Team.OAKLAND)},
				{new TestData("PHI", Team.PHILADELPHIA)},
				{new TestData("PIT", Team.PITTSBURGH)},
				{new TestData("SEA", Team.SEATTLE)},
				{new TestData("SF", Team.SAN_FRANCISCO)},
				{new TestData("TB", Team.TAMPA_BAY)},
				{new TestData("TEN", Team.TENNESSEE)},
				{new TestData("WAS", Team.WASHINGTON)},
				{new TestData("FA", Team.FREE_AGENT)},
				{new TestData("No mathicng data", Team.FREE_AGENT)},
		};
	}

	@Test(dataProvider = "getForValueProvider")
	public void testForValue(TestData data) {
		assertEquals(Team.forValue(data.abbreviation), data.expected);
	}

}