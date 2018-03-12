package com.grahamsfault.stats.server;

import com.grahamsfault.stats.server.command.GameStatImportCommand;
import com.grahamsfault.stats.server.command.PlayerImportCommand;
import com.grahamsfault.stats.server.command.RunETLCommand;
import com.grahamsfault.stats.server.command.TestPredictionsCommand;
import com.grahamsfault.stats.server.factory.StatsApplicationFactory;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.resources.player.NClosestPlayersResource;
import com.grahamsfault.stats.server.resources.player.PlayerByIdResource;
import com.grahamsfault.stats.server.resources.player.PlayerSearchResource;
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
		bootstrap.addCommand(new TestPredictionsCommand());
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
		environment.jersey().register(new NClosestPlayersResource(playerManager, factory.getPredictionManager(configuration)));
	}
}
