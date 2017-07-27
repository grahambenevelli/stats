package com.grahamsfault.nfl.resources.game;

import com.codahale.metrics.annotation.Timed;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.model.GameStats;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/game/{eid}/stats")
@Produces(MediaType.APPLICATION_JSON)
public class GameStatsResource {

    private final StatsManager statsManager;

    public GameStatsResource(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @GET
    @Timed(name = "stats")
    public GameStats stats(@PathParam("eid") String eid) {
        Optional<GameStats> gameStats = statsManager.gameStats(eid);
        if (!gameStats.isPresent()) {
            throw new NotFoundException();
        }
        return gameStats.get();
    }
}
