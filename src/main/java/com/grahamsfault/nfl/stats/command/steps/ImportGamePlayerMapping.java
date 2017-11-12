package com.grahamsfault.nfl.stats.command.steps;

import com.grahamsfault.nfl.stats.api.NflService;
import com.grahamsfault.nfl.stats.api.model.Game;
import com.grahamsfault.nfl.stats.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.stats.manager.GameManager;
import com.grahamsfault.nfl.stats.manager.ImportManager;
import com.grahamsfault.nfl.stats.model.GameStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;

/**
 * Step to import the game stats
 * TODO test
 */
public class ImportGamePlayerMapping implements EtlStep {

	private final static Logger LOG = LoggerFactory.getLogger(ImportGamePlayerMapping.class);

	private final GameManager gameManager;
	private final NflService nflService;
	private final ImportManager importManager;

	public ImportGamePlayerMapping(GameManager gameManager, NflService nflService, ImportManager importManager) {
		this.gameManager = gameManager;
		this.nflService = nflService;
		this.importManager = importManager;
	}

	@Override
	public void run() {
		Set<Game> games = gameManager.allGames();

		int index = 0;
		for (Game game : games) {
			++index;
			if (importManager.hasPlayerIdsBeenImported(game.getEid())) {
				LOG.info("Already imported player ids for " + game.getEid() + " (" + index + " of " + games.size() + ")");
				continue;
			}

			LOG.info("Importing player ids for " + game.getEid() + " (" + index + " of " + games.size() + ")");
			Optional<GameStats> gameStats = gameStats(game.getEid());
			if (!gameStats.isPresent()) {
				LOG.warn("Skipping player ids for game with eid: " + game.getEid() + " (" + index + " of " + games.size() + ")");
				continue;
			}
			persistPlayerIds(game.getEid(), game.getYear(), gameStats.get().getPlayerStatsMap().keySet());
			LOG.info("Persisted player ids for " + game.getEid() + " (" + index + " of " + games.size() + ")");
		}
	}

	private void persistPlayerIds(final String eid, final int year, Set<String> playerIds) {
		playerIds.forEach(playerId -> importManager.recordPlayerIdForImport(year, playerId));
		importManager.markPlayerIdsAsImported(eid);
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
		return Optional.of(
				GameStats.fromGameNotes(eid, gameStatsWrapper.getProfiles().get(eid))
		);
	}

}
