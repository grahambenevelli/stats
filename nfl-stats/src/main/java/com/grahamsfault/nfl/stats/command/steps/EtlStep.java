package com.grahamsfault.nfl.stats.command.steps;

/**
 * An interface for a step in the ETL process
 */
public interface EtlStep {

	/**
	 * Run this step
	 */
	void run();

}
