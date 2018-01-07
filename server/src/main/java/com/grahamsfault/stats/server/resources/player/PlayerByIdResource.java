package com.grahamsfault.stats.server.resources.player;

import com.codahale.metrics.annotation.Timed;
import com.grahamsfault.stats.server.api.model.Player;
import com.grahamsfault.stats.server.manager.PlayerManager;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/player/{playerId}/")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerByIdResource {

    private final PlayerManager playerManager;

    public PlayerByIdResource(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @GET
    @Timed(name = "search")
    // TODO Return internal representation of Player
    public Player search(@PathParam("playerId") Long playerId) {
        Optional<Player> player = playerManager.getById(playerId);
        if (player.isPresent()) {
            return player.get();
        }
        throw new NotFoundException("Player with id " + playerId + " was not found");
    }
}
