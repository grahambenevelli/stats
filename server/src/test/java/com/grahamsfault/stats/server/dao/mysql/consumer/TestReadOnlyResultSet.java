package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.common.collect.Maps;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;

/**
 * The test read only result set
 */
public class TestReadOnlyResultSet extends ReadOnlyResultSet {

	private final Map<String, String> stringValues;
	private final Map<String, Integer> intValues;
	private final Map<String, Long> longValues;
	private final Map<String, URL> urlValues;

	public TestReadOnlyResultSet() {
		super(null);
		stringValues = Maps.newHashMap();
		intValues = Maps.newHashMap();
		longValues = Maps.newHashMap();
		urlValues = Maps.newHashMap();
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, int value) {
		this.intValues.put(key, value);
		return this;
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, long value) {
		this.longValues.put(key, value);
		return this;
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, String value) {
		this.stringValues.put(key, value);
		return  this;
	}

	/**
	 * Set the value for the result set
	 */
	public TestReadOnlyResultSet value(String key, URL value) {
		this.urlValues.put(key, value);
		return  this;
	}

	/**
	 * Get the string value for a given column
	 */
	@Override
	public String getString(String columnLabel) throws SQLException {
		return stringValues.get(columnLabel);
	}

	/**
	 * Get the int value for a given column
	 */
	@Override
	public int getInt(String columnLabel) throws SQLException {
		return intValues.get(columnLabel);
	}

	/**
	 * Get the long value for a given column
	 */
	@Override
	public long getLong(String columnLabel) throws SQLException {
		return longValues.get(columnLabel);
	}

	/**
	 * Get the URL value for a given column
	 */
	@Override
	public URL getURL(String columnLabel) throws SQLException {
		return urlValues.get(columnLabel);
	}
}