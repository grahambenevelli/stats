package com.grahamsfault.nfl.stats;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public abstract class AbstractMysqlIntegrationTest {

	protected static DataSource generateDataSource(String host, int port, String database, String username, String password) {
		String url = String.format("jdbc:mysql://%s:%d/%s", host, port, database);

		MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
		ds.setURL(url);
		ds.setUser(username);
		ds.setPassword(password);

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setServerName(host);
		dataSource.setDatabaseName(database);
		dataSource.setPort(port);

		return dataSource;
	}
}