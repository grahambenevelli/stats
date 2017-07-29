package com.grahamsfault.nfl.command.steps;

import com.grahamsfault.nfl.manager.ImportManager;

import java.util.List;

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