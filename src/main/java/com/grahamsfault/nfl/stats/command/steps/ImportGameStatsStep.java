package com.grahamsfault.nfl.stats.command.steps;

import com.grahamsfault.nfl.stats.api.NflService;
import com.grahamsfault.nfl.stats.api.exception.NflServiceException;
import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.game.GameNotes;
import com.grahamsfault.nfl.stats.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.stats.manager.GameManager;
import com.grahamsfault.nfl.stats.manager.ImportManager;
import com.grahamsfault.nfl.stats.manager.StatsManager;
import com.grahamsfault.nfl.stats.model.GameStats;
import com.grahamsfault.nfl.stats.model.PlayerStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Step to import the game stats
 * TODO test
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
		try {
			GameNotes gameNotes = nflService.getGameNotes(eid);
			return Optional.of(
					GameStats.fromGameNotes(eid, gameNotes)
			);
		} catch (NflServiceException e) {
			// TODO make sure this is only happens when the game can not be found
			return Optional.empty();
		}
	}

}
