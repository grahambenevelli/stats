package com.grahamsfault.nfl.command;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.nfl.command.steps.CompileYearlyStatsStep;
import com.grahamsfault.nfl.command.steps.ImportGameStatsStep;
import com.grahamsfault.nfl.command.steps.ImportGameStep;
import com.grahamsfault.nfl.command.steps.ImportPlayerStep;
import com.grahamsfault.nfl.command.steps.EtlStep;
import com.grahamsfault.nfl.dao.GameDAO;
import com.grahamsfault.nfl.dao.ImportDAO;
import com.grahamsfault.nfl.dao.StatsDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLGameDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLImportDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLPlayerDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.dao.mysql.MySQLStatsDAO;
import com.grahamsfault.nfl.file.GameFileReader;
import com.grahamsfault.nfl.file.PlayerFileReader;
import com.grahamsfault.nfl.manager.GameManager;
import com.grahamsfault.nfl.manager.ImportManager;
import com.grahamsfault.nfl.manager.PlayerManager;
import com.grahamsfault.nfl.manager.StatsManager;
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

	private PlayerManager playerManager;
	private GameManager gameManager;
	private StatsManager statsManager;
	private ImportManager importManager;
	private MySQLPlayerDAO playerDAO;
	private GameDAO gameDAO;
	private StatsDAO statsDAO;
	private ImportDAO importDAO;
	private ObjectMapper objectMapper;
	private DataSource statsDataSource;
	private PlayerFileReader playerFileReader;
	private GameFileReader gameFileReader;
	private NflService nflService;

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

	/**
	 * Get the step to import the game stats into the database
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import game stats
	 */
	protected EtlStep getImportGameStatsStep(StatsConfiguration configuration) {
		GameManager gameManager = getGameManager(configuration);
		StatsManager statsManager = getStatsManager(configuration);
		NflService nflService = getNflService();
		ImportManager importManager = getImportManager(configuration);

		return new ImportGameStatsStep(gameManager, statsManager, nflService, importManager);
	}

	protected EtlStep getCompileYearlyStatsStep(StatsConfiguration configuration) {
		return new CompileYearlyStatsStep(importManager);
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
			PlayerDAO playerDAO = getPlayerDAO(configuration);
			playerManager = new PlayerManager(playerDAO);
		}
		return playerManager;
	}

	/**
	 * Get the manager for business logic around games
	 *
	 * @param configuration The stats server configuration
	 * @return The game manager
	 */
	private GameManager getGameManager(StatsConfiguration configuration) {
		if (gameManager == null) {
			GameDAO gameDAO = getGameDAO(configuration);
			gameManager = new GameManager(gameDAO);
		}
		return gameManager;
	}

	/**
	 * Get the manager fro business logic around stats
	 *
	 * @param configuration The stats server configuration
	 * @return The stats manager
	 */
	private StatsManager getStatsManager(StatsConfiguration configuration) {
		if (statsManager == null) {
			StatsDAO statsDAO = getStatsDAO(configuration);
			statsManager = new StatsManager(statsDAO);
		}
		return statsManager;
	}

	/**
	 * Get the manager for handling imports
	 *
	 * @param configuration The stats server configuration
	 * @return The impor manager
	 */
	private ImportManager getImportManager(StatsConfiguration configuration) {
		if (importManager == null) {
			ImportDAO importDAO = getImportDAO(configuration);
			importManager = new ImportManager(importDAO);
		}
		return importManager;
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

	/**
	 * Get the game DAO
	 *
	 * @param configuration The stats server configuration
	 * @return The game DAO
	 */
	public GameDAO getGameDAO(StatsConfiguration configuration) {
		if (gameDAO == null) {
			gameDAO = new MySQLGameDAO(getStatsDataSource(configuration));
		}
		return gameDAO;
	}

	/**
	 * Get the stats DAO
	 *
	 * @param configuration The stats server configuration
	 * @return The stats DAO
	 */
	private StatsDAO getStatsDAO(StatsConfiguration configuration) {
		if (statsDAO == null) {
			statsDAO = new MySQLStatsDAO(getStatsDataSource(configuration));
		}
		return statsDAO;
	}

	/**
	 * Get the import DAO
	 *
	 * @param configuration The stats server configuration
	 * @return The import DAO
	 */
	private ImportDAO getImportDAO(StatsConfiguration configuration) {
		if (importDAO == null) {
			importDAO = new MySQLImportDAO(getStatsDataSource(configuration));
		}
		return importDAO;
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

	/**
	 * The NFL service object
	 *
	 * @return THe NFL service object
	 */
	public NflService getNflService() {
		if (nflService == null) {
			nflService = new NflService(getObjectMapper());
		}
		return nflService;
	}
}
