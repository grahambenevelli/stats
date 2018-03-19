package com.grahamsfault.stats.server.resources.player;

import com.codahale.metrics.annotation.Timed;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.prediction.util.similarity.impl.EuclideanCalculator;
import com.grahamsfault.prediction.util.similarity.impl.PearsonCalculator;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.PredictionManager;
import com.grahamsfault.stats.server.manager.helper.PlayerCorrelationCalculator;
import com.grahamsfault.stats.server.model.NClosestResults;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/player/{playerId}/closest/{n}/{year}")
@Produces(MediaType.APPLICATION_JSON)
public class NClosestPlayersResource {

    private final PlayerManager playerManager;
    private final PredictionManager predictionManager;

    public NClosestPlayersResource(PlayerManager playerManager, PredictionManager predictionManager) {
        this.playerManager = playerManager;
        this.predictionManager = predictionManager;
    }

    @GET
    @Timed(name = "n-closest-players")
    public NClosestResults nClosest(@PathParam("playerId") Long playerId, @PathParam("n") Integer n, @PathParam("year") Integer year) {
        Optional<Player> player = playerManager.getById(playerId);
        if (!player.isPresent()) {
            throw new NotFoundException("Player with id " + playerId + " was not found");
        }

        Optional<NClosestResults> nClosest = predictionManager.nClosest(new PlayerCorrelationCalculator(new EuclideanCalculator()), player.get(), year, n);
        if (!nClosest.isPresent()) {
            throw new NotFoundException("Stats for player " + playerId + " was not found for year " + year);
        }

        return nClosest.get();
    }
}
