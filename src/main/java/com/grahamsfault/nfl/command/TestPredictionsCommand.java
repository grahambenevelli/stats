package com.grahamsfault.nfl.command;

import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.command.prediction.PredictionExecution;
import com.grahamsfault.nfl.command.prediction.PredictionResults;
import com.grahamsfault.nfl.command.prediction.impl.AverageOnlyPredictionExecution;
import com.grahamsfault.nfl.manager.PredictionManager;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Command to test a prediction process
 */
public class TestPredictionsCommand extends ConfiguredCommand<StatsConfiguration> {

	private static final StatsApplicationFactory factory = StatsApplicationFactory.instance();

	public TestPredictionsCommand() {
		super("test-prediction", "Test the given prediction mechanism");
	}

	@Override
	protected void run(Bootstrap<StatsConfiguration> bootstrap, Namespace namespace, StatsConfiguration configuration) throws Exception {
		PredictionExecution execution = getCurrentTest(configuration);
		PredictionResults results = execution.run();

		PredictionManager predictionManager = factory.getPredictionManager(configuration);
		predictionManager.recordResults(execution.getName(), results);
	}

	/**
	 * Get the current test
	 *
	 * @param configuration The stats server configuration
	 * @return The current prediction execution
	 */
	private PredictionExecution getCurrentTest(StatsConfiguration configuration) {
		return getAverageOnlyPredictionExecution(configuration);
	}

	/**
	 * Get the average only prediciton execution
	 *
	 * @param configuration The stats server configuration
	 * @return The average only prediction execution
	 */
	private PredictionExecution getAverageOnlyPredictionExecution(StatsConfiguration configuration) {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		return new AverageOnlyPredictionExecution(
				factory.getImportManager(configuration),
				factory.getStatsManager(configuration),
				factory.getPlayerManager(configuration),
				factory.getNaiveAverageHelper(configuration)
		);
	}
}
