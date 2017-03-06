package com.grahamsfault.nfl.resources.player;

import com.codahale.metrics.annotation.Timed;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.api.model.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.Set;

@Path("/player/search/")
@Produces(MediaType.APPLICATION_JSON)
public class PlayerSearchResource {

    private final PlayerManager playerManager;

    public PlayerSearchResource(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @GET
    @Timed(name = "search")
    public Set<Player> search(@QueryParam("first_name") String firstName, @QueryParam("last_name") String lastName) {
        return playerManager.searchForPlayer(
                Optional.ofNullable(firstName),
                Optional.ofNullable(lastName)
        );
    }
}
