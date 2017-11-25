package com.grahamsfault.nfl.stats.command.steps;

import com.grahamsfault.nfl.stats.manager.ImportManager;

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
		for(Integer year : years) {
			importManager.compileYearlyStats(year);
		}
	}
}
