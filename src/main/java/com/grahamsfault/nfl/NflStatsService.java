package com.grahamsfault.nfl;

import com.grahamsfault.nfl.resources.HelloWorldResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class NflStatsService extends Service<StatsConfiguration> {

    public static void main(String[] args) throws Exception {
        new NflStatsService().run(args);
    }

    @Override
    public void initialize(Bootstrap<StatsConfiguration> bootstrap) {
        bootstrap.setName("nfl-stats");
    }

    @Override
    public void run(StatsConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        environment.addResource(new HelloWorldResource());
    }
}
