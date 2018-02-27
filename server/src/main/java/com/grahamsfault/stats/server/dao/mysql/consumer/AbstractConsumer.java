package com.grahamsfault.stats.server.dao.mysql.consumer;

import java.sql.SQLException;

public abstract class AbstractConsumer<T> {

	/**
	 * Consume the result set and spit out a T
	 */
	public abstract T consume(ReadOnlyResultSet result) throws SQLException;
}