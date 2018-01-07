package com.grahamsfault.stats.server.command.steps;

import com.grahamsfault.stats.server.api.model.Game;
import com.grahamsfault.stats.server.file.GameFileReader;
import com.grahamsfault.stats.server.manager.GameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Step in the ETL process to import the games played since 2009
 * TODO test
 */
public class ImportGameStep implements EtlStep {

	private final static Logger LOG = LoggerFactory.getLogger(ImportGameStep.class);

	private final GameFileReader gameFileReader;
	private final GameManager gameManager;

	public ImportGameStep(GameFileReader gameFileReader, GameManager gameManager) {
		this.gameFileReader = gameFileReader;
		this.gameManager = gameManager;
	}

	@Override
	public void run() {
		List<Game> games = gameFileReader.allGames();
		int index = 0;
		for(Game game : games) {
			LOG.info("Importing " + game.getEid() + " (" + ++index + " of " + games.size() + ")");
			gameManager.updateGame(game);
		}
	}
}
