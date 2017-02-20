package com.grahamsfault.nfl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.dao.FilePlayerDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class NflStatsService extends Application<StatsConfiguration> {

    public static void main(String[] args) throws Exception {
        new NflStatsService().run(args);
    }

    @Override
    public void initialize(Bootstrap<StatsConfiguration> bootstrap) {
        // TODO add something here?
    }

    @Override
    public void run(StatsConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        PlayerDAO playerDAO = new FilePlayerDAO(mapper);
        PlayerManager playerManager = new PlayerManager(playerDAO);

        environment.jersey().register(new SearchResource(playerManager));
    }
}
