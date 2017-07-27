package com.grahamsfault.nfl;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.command.GameStatImportCommand;
import com.grahamsfault.nfl.command.PlayerImportCommand;
import com.grahamsfault.nfl.command.RunETLCommand;
import com.grahamsfault.nfl.dao.StatsDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLGameDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLStatsDAO;
import com.grahamsfault.nfl.file.GameFileReader;
import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLPlayerDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.file.PlayerFileReader;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.resources.game.GameSearchResource;
import com.grahamsfault.nfl.resources.game.GameStatsResource;
import com.grahamsfault.nfl.resources.player.PlayerSearchResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.sql.DataSource;

public class NflStatsService extends Application<StatsConfiguration> {

	public static void main(String[] args) throws Exception {
		new NflStatsService().run(args);
	}

	@Override
	public void initialize(Bootstrap<StatsConfiguration> bootstrap) {
		bootstrap.addCommand(new PlayerImportCommand());
		bootstrap.addCommand(new GameStatImportCommand());
		bootstrap.addCommand(new RunETLCommand());
		bootstrap.addBundle(new MigrationsBundle<StatsConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(StatsConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});
	}

	@Override
	public void run(StatsConfiguration configuration,
					Environment environment) throws ClassNotFoundException {
		DataSource stats = configuration.getDataSourceFactory().build(new MetricRegistry(), "stats");

		GameDAO gameDAO = new MySQLGameDAO(stats);
		PlayerDAO playerDAO = new MySQLPlayerDAO(stats);
		StatsDAO statsDAO = new MySQLStatsDAO(stats);

		PlayerManager playerManager = new PlayerManager(playerDAO);
		GameManager gameManager = new GameManager(gameDAO);
		StatsManager statsManager = new StatsManager(statsDAO);

		environment.jersey().register(new PlayerSearchResource(playerManager));
		environment.jersey().register(new GameSearchResource(gameManager));
		environment.jersey().register(new GameStatsResource(statsManager));
	}
}
