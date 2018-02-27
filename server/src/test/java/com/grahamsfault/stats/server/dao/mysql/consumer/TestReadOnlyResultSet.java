package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.google.common.collect.Maps;

import java.sql.SQLException;
import java.util.Map;

/**
 * The test read only result set
 */
public class TestReadOnlyResultSet extends ReadOnlyResultSet {

	private Map<String, String> stringValues;
	private Map<String, Integer> intValues;

	public TestReadOnlyResultSet() {
		super(null);
		stringValues = Maps.newHashMap();
		intValues = Maps.newHashMap();
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, int integer) {
		this.intValues.put(key, integer);
		return this;
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, String string) {
		this.stringValues.put(key, string);
		return  this;
	}

	/**
	 * Get the string value for a given column
	 */
	public String getString(String columnLabel) throws SQLException {
		return stringValues.get(columnLabel);
	}

	/**
	 * Get the int value for a given column
	 */
	public int getInt(String columnLabel) throws SQLException {
		return intValues.get(columnLabel);
	}

}