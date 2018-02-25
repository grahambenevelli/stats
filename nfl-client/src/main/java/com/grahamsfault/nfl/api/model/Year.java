package com.grahamsfault.nfl.api.model;

public enum  Year {
	YEAR_2009(2009),
	YEAR_2010(2010),
	YEAR_2011(2011),
	YEAR_2012(2012),
	YEAR_2013(2013),
	YEAR_2014(2014),
	YEAR_2015(2015),
	YEAR_2016(2016),
	YEAR_2017(2017),
	YEAR_2018(2018),
	YEAR_2019(2019),
	YEAR_2020(2020),
	;

	private final int year;

	Year(int year) {
		this.year = year;
	}

	public int intValue() {
		return year;
	}

	public static Year fromIntValue(int intValue) {
		for (Year year : Year.values()) {
			if (year.intValue() == intValue) {
				return year;
			}
		}

		throw new IllegalArgumentException();
	}
}
