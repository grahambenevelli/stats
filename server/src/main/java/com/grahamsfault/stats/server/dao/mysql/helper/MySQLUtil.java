package com.grahamsfault.stats.server.dao.mysql.helper;

/**
 * Utility methods for working with MySQL
 */
public class MySQLUtil {

	/**
	 * Return either the value passed in, or 0 if null
	 *
	 * @param value The value
	 * @return The value or zero
	 */
	public static double zeroOrNull(Double value) {
		if (value == null) {
			return 0;
		}
		return value;
	}

	/**
	 * Return either the value passed in, or 0 if null
	 *
	 * @param value The value
	 * @return The value or zero
	 */
	public static long zeroOrNull(Long value) {
		if (value == null) {
			return 0;
		}
		return value;
	}
}
