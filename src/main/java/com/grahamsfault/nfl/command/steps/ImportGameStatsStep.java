package com.grahamsfault.nfl.command.steps;

import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.game.GameNotes;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.player.RawStats;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.manager.ImportManager;
import com.grahamsfault.nfl.manager.StatsManager;
import com.grahamsfault.nfl.manager.builder.GameStatsBuilder;
import com.grahamsfault.nfl.model.GameStats;
import com.grahamsfault.nfl.model.PlayerStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Step to import the game stats
 */
public class ImportGameStatsStep implements EtlStep {

	private final static Logger LOG = LoggerFactory.getLogger(ImportGameStatsStep.class);

	private final GameManager gameManager;
	private final StatsManager statsManager;
	private final NflService nflService;
	private final ImportManager importManager;

	public ImportGameStatsStep(GameManager gameManager, StatsManager statsManager, NflService nflService, ImportManager importManager) {
		this.gameManager = gameManager;
		this.statsManager = statsManager;
		this.nflService = nflService;
		this.importManager = importManager;
	}

	@Override
	public void run() {
		Set<Game> games = gameManager.allGames();

		int index = 0;
		for (Game game : games) {
			++index;
			if (importManager.hasImported(game.getEid())) {
				LOG.info("Already imported " + game.getEid() + " (" + index + " of " + games.size() + ")");
				continue;
			}


			LOG.info("Importing stats for " + game.getEid() + " (" + index + " of " + games.size() + ")");
			Optional<GameStats> gameStats = gameStats(game.getEid());
			if (!gameStats.isPresent()) {
				LOG.warn("Skipping game with eid: " + game.getEid() + " (" + index + " of " + games.size() + ")");
				return;
			}
			persistGameStats(game, gameStats.get());
			LOG.info("Persisted data for " + game.getEid() + " (" + index + " of " + games.size() + ")");
		}
	}

	/**
	 * Save the data for the game stats
	 *
	 * @param game The game these stats are for
	 * @param gameStats The game stats to save
	 */
	private void persistGameStats(Game game, GameStats gameStats) {
		Map<String, PlayerStats> playerStatsMap = gameStats.getPlayerStatsMap();
		playerStatsMap.entrySet().forEach(
				stringPlayerStatsEntry -> statsManager.updateGameStats(game, stringPlayerStatsEntry.getValue())
		);
		importManager.markAsImported(game.getEid());
	}

	/**
	 * Get the games stats for the given EID
	 *
	 * @param eid The id of the game
	 * @return An optional version of the game's stats
	 */
	private Optional<GameStats> gameStats(String eid) {
		GameStatsWrapper gameStatsWrapper = nflService.gameStats(eid);
		if (!gameStatsWrapper.getProfiles().containsKey(eid)) {
			return Optional.empty();
		}
		return Optional.of(convertToGameStats(gameStatsWrapper.getProfiles().get(eid)));
	}

	/**
	 * Convert the games notes into the game stats object
	 *
	 * @param gameNotes The game's notes returned from the NFL service
	 * @return The game stats
	 */
	private GameStats convertToGameStats(GameNotes gameNotes) {
		GameStatsBuilder gameStatsFactory = new GameStatsBuilder();

		if (gameNotes.getHome().getTeamStats().getPassing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getPassing().getStats().entrySet()) {
				gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getHome().getTeamStats().getRushing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getRushing().getStats().entrySet()) {
				gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getHome().getTeamStats().getReceiving() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getReceiving().getStats().entrySet()) {
				gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getHome().getTeamStats().getFumbles() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getHome().getTeamStats().getFumbles().getStats().entrySet()) {
				gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getAway().getTeamStats().getPassing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getPassing().getStats().entrySet()) {
				gameStatsFactory.addPassing(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getAway().getTeamStats().getRushing() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getRushing().getStats().entrySet()) {
				gameStatsFactory.addRushing(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getAway().getTeamStats().getReceiving() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getReceiving().getStats().entrySet()) {
				gameStatsFactory.addReceiving(entry.getKey(), entry.getValue());
			}
		}

		if (gameNotes.getAway().getTeamStats().getFumbles() != null) {
			for (Map.Entry<String, RawStats> entry : gameNotes.getAway().getTeamStats().getFumbles().getStats().entrySet()) {
				gameStatsFactory.addFumbles(entry.getKey(), entry.getValue());
			}
		}

		return gameStatsFactory.build();
	}
}
