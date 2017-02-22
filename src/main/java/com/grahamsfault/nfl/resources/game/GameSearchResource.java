package com.grahamsfault.nfl.resources.game;

import com.codahale.metrics.annotation.Timed;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.model.Game;
import com.grahamsfault.nfl.model.Team;
import com.grahamsfault.nfl.model.game.GameType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/game/search/")
@Produces(MediaType.APPLICATION_JSON)
public class GameSearchResource {

    private final GameManager gameManager;

    public GameSearchResource(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GET
    @Timed(name = "search")
    public List<Game> search(@QueryParam("year") int year,
                             @QueryParam("week") int week,
                             @QueryParam("home") Team home,
                             @QueryParam("away") Team away,
                             @QueryParam("type") GameType type) {
        return gameManager.searchGames(
                year,
                Optional.ofNullable(type).orElse(GameType.REG),
                Optional.ofNullable(week),
                Optional.ofNullable(home),
                Optional.ofNullable(away)
        );
    }
}
