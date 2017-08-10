package com.grahamsfault.nfl.command.prediction;

/**
 * The abstract class for a prediction execution
 */
public abstract class PredictionExecution {

	private final String name;

	protected PredictionExecution(String name) {
		this.name = name;
	}

	/**
	 * Run the given execution and return the results
	 *
	 * @return The prediction results
	 */
	public abstract PredictionResults run();

	/**
	 * Get the name of the execution
	 *
	 * @return The name of the execution
	 */
	public String getName() {
		return name;
	}
}
