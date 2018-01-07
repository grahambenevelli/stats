package com.grahamsfault.stats.server.command;

import com.grahamsfault.stats.server.StatsConfiguration;
import com.grahamsfault.nfl.api.NflService;
import com.grahamsfault.stats.server.command.steps.CompileYearlyStatsStep;
import com.grahamsfault.stats.server.command.steps.EtlStep;
import com.grahamsfault.stats.server.command.steps.ImportGamePlayerMapping;
import com.grahamsfault.stats.server.command.steps.ImportGameStatsStep;
import com.grahamsfault.stats.server.command.steps.ImportGameStep;
import com.grahamsfault.stats.server.command.steps.ImportPlayerStep;
import com.grahamsfault.stats.server.factory.StatsApplicationFactory;
import com.grahamsfault.stats.server.file.GameFileReader;
import com.grahamsfault.stats.server.manager.GameManager;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;
import com.grahamsfault.stats.server.manager.StatsManager;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import java.util.List;

/**
 * A command that can be divided into individual ETL steps
 */
public abstract class StepCommand extends ConfiguredCommand<StatsConfiguration> {

	private StatsApplicationFactory factory;

	protected StepCommand(String name, String description) {
		super(name, description);
		factory = StatsApplicationFactory.instance();
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
		ImportManager importManager = factory.getImportManager(configuration);
		NflService service = factory.getNflService();
		PlayerManager playerManager = factory.getPlayerManager(configuration);

		return new ImportPlayerStep(importManager, playerManager, service);
	}

	/**
	 * Get the step to import games into the database, reading from a file
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import games
	 */
	protected EtlStep getImportGameStep(StatsConfiguration configuration) {
		GameFileReader gameFileReader = factory.getGameFileReader();
		GameManager gameManager = factory.getGameManager(configuration);

		return new ImportGameStep(gameFileReader, gameManager);
	}

	/**
	 * Get the step to import the game stats into the database
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import game stats
	 */
	protected EtlStep getImportGameStatsStep(StatsConfiguration configuration) {
		GameManager gameManager = factory.getGameManager(configuration);
		StatsManager statsManager = factory.getStatsManager(configuration);
		NflService nflService = factory.getNflService();
		ImportManager importManager = factory.getImportManager(configuration);

		return new ImportGameStatsStep(gameManager, statsManager, nflService, importManager);
	}

	/**
	 * Get the step to compile the yearly stats
	 *
	 * @param configuration The stats server configuration
	 * @return The step to compile the yearly stats
	 */
	protected EtlStep getCompileYearlyStatsStep(StatsConfiguration configuration) {
		return new CompileYearlyStatsStep(factory.getImportManager(configuration));
	}

	/**
	 * Get the step to import player ids from games
	 *
	 * @param configuration The stats server configuration
	 * @return The step to import player ids from games
	 */
	protected EtlStep getImportGamePlayerMapping(StatsConfiguration configuration) {
		GameManager gameManager = factory.getGameManager(configuration);
		NflService nflService = factory.getNflService();
		ImportManager importManager = factory.getImportManager(configuration);

		return new ImportGamePlayerMapping(gameManager, nflService, importManager);
	}
}
