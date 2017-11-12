package com.grahamsfault.nfl.stats.command.steps;

import com.grahamsfault.nfl.stats.api.NflService;
import com.grahamsfault.nfl.stats.api.exception.NflServiceException;
import com.grahamsfault.nfl.stats.api.exception.NflServicePlayerDataException;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.manager.ImportManager;
import com.grahamsfault.nfl.stats.manager.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Step in the ETL process that imports the already known players, this is pulling from a file so it does not pull
 * in new players, those will have to be manually updated until we have a way to pull that from the NFL site.
 * TODO test
 */
public class ImportPlayerStep implements EtlStep {

	private final static Logger LOG = LoggerFactory.getLogger(ImportGameStep.class);

	private final ImportManager importManager;
	private final NflService nflService;
	private final PlayerManager playerManager;

	public ImportPlayerStep(ImportManager importManager, PlayerManager playerManager, NflService nflService) {
		this.importManager = importManager;
		this.playerManager = playerManager;
		this.nflService = nflService;
	}

	@Override
	public void run() {
		List<String> playerIds = importManager.getPlayerIds();
		LOG.info("There are " + playerIds.size() + " player left to import");
		playerIds.forEach(this::importBasicInformation);
	}

	/**
	 * Import the basic information of the players
	 *
	 * @param playerId The id of the player to import
	 */
	private void importBasicInformation(String playerId) {
		LOG.info("Importing " + playerId);
		int errors = 0;
		Exception finalException = null;
		while (errors < 3) {
			try {
				Player player = nflService.getPlayerBasicInfo(playerId);
				playerManager.updatePlayer(player);
				importManager.markPlayerBasicInfoImported(playerId);
				return;
			} catch (IOException e) {
				finalException = e;
				errors++;
			} catch (NflServicePlayerDataException e) {
				importManager.markPlayerBasicInfoImported(playerId);
				return;
			} catch (NflServiceException e) {
				finalException = e;
				errors++;
			}
		}

		throw new RuntimeException(finalException);
	}
}
