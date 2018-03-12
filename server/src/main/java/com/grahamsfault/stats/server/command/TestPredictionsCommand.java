package com.grahamsfault.stats.server.command;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.prediction.util.similarity.impl.EuclideanCalculator;
import com.grahamsfault.prediction.util.similarity.impl.PearsonCalculator;
import com.grahamsfault.stats.server.StatsConfiguration;
import com.grahamsfault.stats.server.command.prediction.PredictionExecution;
import com.grahamsfault.stats.server.command.prediction.PredictionResults;
import com.grahamsfault.stats.server.command.prediction.impl.NClosestPredictionExecution;
import com.grahamsfault.stats.server.command.prediction.impl.NaiveAverageOnlyPredictionExecution;
import com.grahamsfault.stats.server.command.prediction.impl.NaiveRepeatStatsPredictionExecution;
import com.grahamsfault.stats.server.command.prediction.impl.QualifyingAveragePredictionExecution;
import com.grahamsfault.stats.server.factory.StatsApplicationFactory;
import com.grahamsfault.stats.server.manager.PredictionManager;
import com.grahamsfault.stats.server.manager.helper.QualifyingNumbersHelper;
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
		return ImmutableList.<PredictionExecution>builder()
				//.add(getAverageOnlyPredictionExecution(configuration))
				//.add(getQualifyingAveragePredictionExecution(configuration))
				//.add(getRepeatStatsPredictionExecution(configuration))
				.add(getNClosestPredictionExecution(configuration))
				.build();
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

	/**
	 * Get the repeat stats prediction execution
	 *
	 * @param configuration The stats server configuration
	 * @return The average only prediction execution
	 */
	private NClosestPredictionExecution getNClosestPredictionExecution(StatsConfiguration configuration) {
		StatsApplicationFactory factory = StatsApplicationFactory.instance();
		return new NClosestPredictionExecution(
				10,
				new EuclideanCalculator(),
				factory.getPlayerManager(configuration),
				factory.getImportManager(configuration),
				factory.getStatsManager(configuration),
				factory.getPredictionManager(configuration));
	}
}
