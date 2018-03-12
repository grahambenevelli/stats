package com.grahamsfault.stats.server.factory;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.stats.server.StatsConfiguration;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.stats.server.dao.GameDAO;
import com.grahamsfault.stats.server.dao.ImportDAO;
import com.grahamsfault.stats.server.dao.PlayerDAO;
import com.grahamsfault.stats.server.dao.PredictionDAO;
import com.grahamsfault.stats.server.dao.StatsDAO;
import com.grahamsfault.stats.server.dao.mysql.MySQLGameDAO;
import com.grahamsfault.stats.server.dao.mysql.MySQLImportDAO;
import com.grahamsfault.stats.server.dao.mysql.MySQLPlayerDAO;
import com.grahamsfault.stats.server.dao.mysql.MySQLPredictionDAO;
import com.grahamsfault.stats.server.dao.mysql.MySQLStatsDAO;
import com.grahamsfault.stats.server.file.GameFileReader;
import com.grahamsfault.stats.server.manager.GameManager;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.PredictionManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import com.grahamsfault.stats.server.manager.helper.QualifyingNumbersHelper;
import com.grahamsfault.stats.server.manager.helper.average.NaiveAverageHelper;
import com.grahamsfault.stats.server.manager.helper.average.QualifyingAverageHelper;

import javax.sql.DataSource;
import javax.ws.rs.client.ClientBuilder;

/**
 * The factory object to build application logic
 */
public class StatsApplicationFactory {

	private static StatsApplicationFactory factory = new StatsApplicationFactory();

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
	private GameFileReader gameFileReader;
	private NflService nflService;
	private PredictionManager predictionManager;
	private PredictionDAO predictionDAO;
	private NaiveAverageHelper naiveAverageHelper;
	private QualifyingAverageHelper qualifyingAverageHelper;

	private StatsApplicationFactory() {}

	public static StatsApplicationFactory instance() {
		return factory;
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
	public PlayerManager getPlayerManager(StatsConfiguration configuration) {
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
	public GameManager getGameManager(StatsConfiguration configuration) {
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
	public StatsManager getStatsManager(StatsConfiguration configuration) {
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
	public ImportManager getImportManager(StatsConfiguration configuration) {
		if (importManager == null) {
			ImportDAO importDAO = getImportDAO(configuration);
			importManager = new ImportManager(importDAO);
		}
		return importManager;
	}

	public PredictionManager getPredictionManager(StatsConfiguration configuration) {
		if (predictionManager == null) {
			PredictionDAO predictionDAO = getPredictionDAO(configuration);
			StatsManager statsManager = getStatsManager(configuration);
			ImportManager importManager = getImportManager(configuration);
			predictionManager = new PredictionManager(predictionDAO, statsManager, importManager);
		}
		return predictionManager;
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
	public MySQLPlayerDAO getPlayerDAO(StatsConfiguration configuration) {
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
	public StatsDAO getStatsDAO(StatsConfiguration configuration) {
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
	public ImportDAO getImportDAO(StatsConfiguration configuration) {
		if (importDAO == null) {
			importDAO = new MySQLImportDAO(getStatsDataSource(configuration));
		}
		return importDAO;
	}

	/**
	 * Get the prediction DAO
	 *
	 * @param configuration The stats server configuration
	 * @return The prediction DAO
	 */
	public PredictionDAO getPredictionDAO(StatsConfiguration configuration) {
		if (predictionDAO == null) {
			predictionDAO = new MySQLPredictionDAO(getStatsDataSource(configuration));
		}
		return predictionDAO;
	}

	/*
	 * The getter methods for helper classes
	 */

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
	public DataSource getStatsDataSource(StatsConfiguration configuration) {
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
	public ObjectMapper getObjectMapper() {
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
			nflService = new NflService(ClientBuilder.newClient());
		}
		return nflService;
	}

	/**
	 * Get the naive average helper
	 *
	 * @param configuration The stats server configuration
	 * @return The naive average helper
	 */
	public NaiveAverageHelper getNaiveAverageHelper(StatsConfiguration configuration) {
		if (naiveAverageHelper == null) {
			naiveAverageHelper = new NaiveAverageHelper(
					getPlayerManager(configuration),
					getStatsManager(configuration)
			);
		}
		return naiveAverageHelper;
	}

	/**
	 * Get the qualifying average helper
	 *
	 * @param configuration The stats server configuration
	 * @return The qualifying average helper
	 */
	public QualifyingAverageHelper getQualifyingAverageHelper(QualifyingNumbersHelper qualifyingNumbersHelper, StatsConfiguration configuration) {
		if (qualifyingAverageHelper == null) {
			qualifyingAverageHelper = new QualifyingAverageHelper(
					getPlayerManager(configuration),
					getStatsManager(configuration),
					qualifyingNumbersHelper
			);
		}
		return qualifyingAverageHelper;
	}
}
