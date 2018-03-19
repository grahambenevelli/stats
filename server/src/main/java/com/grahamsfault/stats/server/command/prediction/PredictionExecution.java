package com.grahamsfault.stats.server.command.prediction;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.stats.server.manager.ImportManager;
import com.grahamsfault.stats.server.manager.PlayerManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The abstract class for a prediction execution
 */
public abstract class PredictionExecution {

	private final String name;
	private final String description;

	protected final ImportManager importManager;
	protected final PlayerManager playerManager;

	protected PredictionExecution(
			String name,
			String description,
			ImportManager importManager,
			PlayerManager playerManager
	) {
		this.name = name;
		this.description = description;
		this.importManager = importManager;
		this.playerManager = playerManager;
	}

	/**
	 * Run the given execution and return the results
	 *
	 * @return The prediction results
	 */
	public PredictionResults run() {
		PredictionResults.Builder predictionBuilder = PredictionResults.builder();

		for (Integer year : getPredictionYears()) {
			for (Player player : playerManager.getPlayersPerYear(year - 1)) {
				Optional<PredictionResults.Unit> unit = entry(player, year);
				if (unit.isPresent()) {
					predictionBuilder.increment(unit.get());
				}
			}
		}

		return predictionBuilder.build();
	}

	/**
	 * Get the name of the execution
	 *
	 * @return The name of the execution
	 */
	public String getName() {
		return name;
	}

	/**
	 * Process a single entry of Player Year tuple
	 *
	 * @param player The player to predict
	 * @param year The year we are predicting
	 * @return An Optional PredictionResults.Unit
	 */
	protected abstract Optional<PredictionResults.Unit> entry(Player player, Integer year);

	/**
	 * Get the prediction years for this execution
	 *
	 * @return The prediction years
	 */
	protected List<Integer> getPredictionYears() {
		return importManager.getYears()
				.stream()
				.skip(1)
				.collect(Collectors.toList());
	}

	/**
	 * Get the description of the execution
	 *
	 * @return The description
	 */
	public String getDescription() {
		return description;
	}
}
