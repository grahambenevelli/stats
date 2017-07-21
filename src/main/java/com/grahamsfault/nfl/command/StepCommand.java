package com.grahamsfault.nfl.command;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.command.steps.ImportGameStep;
import com.grahamsfault.nfl.command.steps.ImportPlayerStep;
import com.grahamsfault.nfl.command.steps.EtlStep;
import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLGameDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLPlayerDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.file.GameFileReader;
import com.grahamsfault.nfl.file.PlayerFileReader;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.manager.PlayerManager;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import javax.sql.DataSource;
import java.util.List;

/**
 * A command that can be divided into individual ETL steps
 */
public abstract class StepCommand extends ConfiguredCommand<StatsConfiguration> {

	private ObjectMapper objectMapper;
	private DataSource statsDataSource;
	private PlayerFileReader playerFileReader;
	private GameFileReader gameFileReader;
	private MySQLPlayerDAO playerDAO;
	private PlayerManager playerManager;
	private GameManager gameManager;
	private GameDAO gameDAO;

	protected StepCommand(String name, String description) {
		super(name, description);
	}

	@Override
	public void configure(Subparser subparser) {
		super.configure(subparser);
	}

	@Override
	protected void run(Bootstrap bootstrap, Namespace namespace, StatsConfiguration configuration) throws Exception {
		this.steps(configuration).forEach(EtlStep::run);
	}

	/**
	 * Get the steps to run in this command in order
	 *
	 * @param configuration The configuration of that stats server
	 * @return The list of steps
	 */
	public abstract List<EtlStep> steps(StatsConfiguration configuration);

	/*
	 * The getter methods for steps
	 */

	/**
	 * Get the step to import players into the database, reading from a file
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import players
	 */
	protected ImportPlayerStep getImportPlayerStep(StatsConfiguration configuration) {
		PlayerFileReader playerFileReader = getPlayerFileReader();
		PlayerManager playerManager = getPlayerManager(configuration);

		return new ImportPlayerStep(playerFileReader, playerManager);
	}

	/**
	 * Get the step to import games into the database, reading from a file
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import games
	 */
	protected EtlStep getImportGameStep(StatsConfiguration configuration) {
		GameFileReader gameFileReader = getGameFileReader();
		GameManager gameManager = getGameManager(configuration);

		return new ImportGameStep(gameFileReader, gameManager);
	}

	/*
	 * The getter method for managers
	 */

	/**
	 * Get the manager for reading and writing players
	 *
	 * @param configuration The stats server configuration
	 * @return The player manager
	 */
	private PlayerManager getPlayerManager(StatsConfiguration configuration) {
		if (playerManager == null) {
			PlayerFileReader playerFileReader = getPlayerFileReader();
			PlayerDAO playerDAO = getPlayerDAO(configuration);
			playerManager = new PlayerManager(playerFileReader, playerDAO);
		}
		return playerManager;
	}

	public GameManager getGameManager(StatsConfiguration configuration) {
		if (gameManager == null) {
			GameDAO gameDAO = getGameDAO(configuration);
			gameManager = new GameManager(gameDAO);
		}
		return gameManager;
	}

	/*
	 * The getter method for DAOs
	 */

	/**
	 * Get the player DAO
	 *
	 * @param configuration The stats server configuration
	 * @return The player DAO
	 */
	private MySQLPlayerDAO getPlayerDAO(StatsConfiguration configuration) {
		if (playerDAO == null) {
			DataSource stats = getStatsDataSource(configuration);
			playerDAO = new MySQLPlayerDAO(stats);
		}
		return playerDAO;
	}

	public GameDAO getGameDAO(StatsConfiguration configuration) {
		if (gameDAO == null) {
			gameDAO = new MySQLGameDAO(getStatsDataSource(configuration));
		}
		return gameDAO;
	}

	/*
	 * The getter methods for helper classes
	 */

	/**
	 * Get the player file reader
	 * @return The player file reader
	 */
	private PlayerFileReader getPlayerFileReader() {
		if (playerFileReader == null) {
			playerFileReader = new PlayerFileReader(getObjectMapper());
		}
		return playerFileReader;
	}

	/**
	 * Get the game file reader
	 * @return The game file reader
	 */
	public GameFileReader getGameFileReader() {
		if (gameFileReader == null) {
			gameFileReader = new GameFileReader(getObjectMapper());
		}
		return gameFileReader;
	}

	/**
	 * Get the stats database data source
	 *
	 * @param configuration The stats server configuration
	 * @return The stats database data source
	 */
	private DataSource getStatsDataSource(StatsConfiguration configuration) {
		if (statsDataSource == null) {
			statsDataSource = configuration.getDataSourceFactory().build(new MetricRegistry(), "stats");
		}
		return statsDataSource;
	}

	/**
	 * Get the obejct mapper
	 *
	 * @return The object mapper
	 */
	private ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}
}
