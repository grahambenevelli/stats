package com.grahamsfault.nfl.stats.command;

import com.google.common.collect.Lists;
import com.grahamsfault.nfl.stats.StatsConfiguration;
import com.grahamsfault.nfl.stats.command.prediction.PredictionExecution;
import com.grahamsfault.nfl.stats.command.prediction.PredictionResults;
import com.grahamsfault.nfl.stats.command.prediction.impl.NaiveAverageOnlyPredictionExecution;
import com.grahamsfault.nfl.stats.command.prediction.impl.NaiveRepeatStatsPredictionExecution;
import com.grahamsfault.nfl.stats.command.prediction.impl.QualifyingAveragePredictionExecution;
import com.grahamsfault.nfl.stats.factory.StatsApplicationFactory;
import com.grahamsfault.nfl.stats.manager.PredictionManager;
import com.grahamsfault.nfl.stats.manager.helper.QualifyingNumbersHelper;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Command to test a prediction process
 */
public class TestPredictionsCommand extends ConfiguredCommand<StatsConfiguration> {

	private final static Logger LOG = LoggerFactory.getLogger(TestPredictionsCommand.class);

	private static final StatsApplicationFactory factory = StatsApplicationFactory.instance();

	public TestPredictionsCommand() {
		super("test-prediction", "Test the given prediction mechanism");
	}

	@Override
	protected void run(Bootstrap<StatsConfiguration> bootstrap, Namespace namespace, StatsConfiguration configuration) throws Exception {
		List<PredictionExecution> executions = getCurrentTests(configuration);
		for (PredictionExecution execution : executions) {
			LOG.info("Executing prediction execution: {}", execution.getName());
			PredictionResults results = execution.run();

			PredictionManager predictionManager = factory.getPredictionManager(configuration);
			predictionManager.recordResults(execution.getName(), results);
		}
	}

	/**
	 * Get the current test
	 *
	 * @param configuration The stats server configuration
	 * @return The current prediction execution
	 */
	private List<PredictionExecution> getCurrentTests(StatsConfiguration configuration) {
		return Lists.newArrayList(
				getAverageOnlyPredictionExecution(configuration),
				getQualifyingAveragePredictionExecution(configuration),
				getRepeatStatsPredictionExecution(configuration)
		);
	}

	/**
	 * Get the average only prediciton execution
	 *
	 * @param configuration The stats server configuration
	 * @return The average only prediction execution
	 */
	private PredictionExecution getAverageOnlyPredictionExecution(StatsConfiguration configuration) {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		return new NaiveAverageOnlyPredictionExecution(
				factory.getImportManager(configuration),
				factory.getStatsManager(configuration),
				factory.getPlayerManager(configuration),
				factory.getNaiveAverageHelper(configuration)
		);
	}

	/**
	 * Get the average only prediciton execution
	 *
	 * @param configuration The stats server configuration
	 * @return The average only prediction execution
	 */
	private PredictionExecution getQualifyingAveragePredictionExecution(StatsConfiguration configuration) {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		QualifyingNumbersHelper qualifyingNumbersHelper = new QualifyingNumbersHelper();
		return new QualifyingAveragePredictionExecution(
				factory.getImportManager(configuration),
				factory.getStatsManager(configuration),
				factory.getPlayerManager(configuration),
				qualifyingNumbersHelper,
				factory.getNaiveAverageHelper(configuration),
				factory.getQualifyingAverageHelper(qualifyingNumbersHelper, configuration)
		);
	}

	/**
	 * Get the repeat stats prediction execution
	 *
	 * @param configuration The stats server configuration
	 * @return The average only prediction execution
	 */
	private PredictionExecution getRepeatStatsPredictionExecution(StatsConfiguration configuration) {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		return new NaiveRepeatStatsPredictionExecution(
				factory.getImportManager(configuration),
				factory.getStatsManager(configuration),
				factory.getPlayerManager(configuration)
		);
	}
}
