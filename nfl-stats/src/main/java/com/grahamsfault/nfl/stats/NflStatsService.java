package com.grahamsfault.nfl.stats;

import com.grahamsfault.nfl.stats.command.GameStatImportCommand;
import com.grahamsfault.nfl.stats.command.PlayerImportCommand;
import com.grahamsfault.nfl.stats.command.RunETLCommand;
import com.grahamsfault.nfl.stats.factory.StatsApplicationFactory;
import com.grahamsfault.nfl.stats.manager.PlayerManager;
import com.grahamsfault.nfl.stats.resources.player.PlayerByIdResource;
import com.grahamsfault.nfl.stats.resources.player.PlayerSearchResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
		StatsApplicationFactory factory = StatsApplicationFactory.instance();

		PlayerManager playerManager = factory.getPlayerManager(configuration);

		environment.jersey().register(new PlayerSearchResource(factory.getPlayerManager(configuration)));
		environment.jersey().register(new PlayerByIdResource(playerManager));
	}
}
