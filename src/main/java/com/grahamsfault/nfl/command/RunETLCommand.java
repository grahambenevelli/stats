package com.grahamsfault.nfl.command;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.command.steps.EtlStep;

import java.util.List;

/**
 * This runs players and stats and condenses them into meaningful stats.
 *
 * Current steps
 * - Import players
 */
public class RunETLCommand extends StepCommand {

	public RunETLCommand() {
		super("run-etl", "Run the full ETL to import, convert and save stats.");
	}

	@Override
	public List<EtlStep> steps(StatsConfiguration configuration) {
		return ImmutableList.<EtlStep>builder()
				.add(getImportPlayerStep(configuration))
				.add(getImportGameStep(configuration))
				.add(getImportGameStatsStep(configuration))
				// 2. Import game stats 4. add drive stats
				// 3. Compile to yearly stats
				.build();
	}
}
