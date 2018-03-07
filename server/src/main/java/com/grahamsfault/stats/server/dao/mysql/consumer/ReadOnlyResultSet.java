package com.grahamsfault.stats.server.dao.mysql.consumer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadOnlyResultSet {

	private final ResultSet resultSet;

	protected ReadOnlyResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public static ReadOnlyResultSet of(ResultSet resultSet) {
		return new ReadOnlyResultSet(resultSet);
	}

	public String getString(String columnLabel) throws SQLException {
		return resultSet.getString(columnLabel);
	}

	public int getInt(String columnLabel) throws SQLException {
		return resultSet.getInt(columnLabel);
	}

	public long getLong(String columnLabel) throws SQLException {
		return resultSet.getLong(columnLabel);
	}

	public URL getURL(String columnLabel) throws SQLException {
		return resultSet.getURL(columnLabel);
	}
}