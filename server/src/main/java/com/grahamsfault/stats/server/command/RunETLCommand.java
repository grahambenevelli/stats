package com.grahamsfault.stats.server.command;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.stats.server.StatsConfiguration;
import com.grahamsfault.stats.server.command.steps.EtlStep;

import java.util.List;

/**
 * This runs players and stats and condenses them into meaningful stats.
 *
 * Current steps
 * - Import games
 * - Import player ids from games
 * - Import players
 * - Import Game Stats
 * - Compile yearly stats
 */
public class RunETLCommand extends StepCommand {

	public RunETLCommand() {
		super("run-etl", "Run the full ETL to import, convert and save stats.");
	}

	@Override
	public List<EtlStep> steps(StatsConfiguration configuration) {
		return ImmutableList.<EtlStep>builder()
				.add(getImportGameStep(configuration))
				.add(getImportGamePlayerMapping(configuration))
				.add(getImportPlayerStep(configuration))
				.add(getImportGameStatsStep(configuration))
				// 4. add drive stats
				// 5. get the games played
				.add(getCompileYearlyStatsStep(configuration))
				// Draft pick taken
				// Age
				.build();
	}
}
