package com.grahamsfault.stats.server.command.steps;

import com.grahamsfault.stats.server.manager.ImportManager;

import java.util.List;

/**
 * Pull in the weekly stats for players and save the yearly stats
 * TODO test
 */
public class CompileYearlyStatsStep implements EtlStep {

	private final ImportManager importManager;

	public CompileYearlyStatsStep(ImportManager importManager) {
		this.importManager = importManager;
	}

	@Override
	public void run() {
		List<Integer> years = importManager.getYears();
		importManager.truncateYearlyStats();
		for(Integer year : years) {
			importManager.compileYearlyStats(year);
		}
	}
}
