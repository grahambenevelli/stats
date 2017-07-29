package com.grahamsfault.nfl.command;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.command.steps.EtlStep;

import java.util.List;

/**
 * A command for directly importing players into the system.
 */
public class GameStatImportCommand extends StepCommand {

	public GameStatImportCommand() {
		super("import-game-stats", "Import the stats at a game level");
	}

	@Override
	public List<EtlStep> steps(StatsConfiguration configuration) {
		return ImmutableList.<EtlStep>builder()
				.add(getImportGameStatsStep(configuration))
				.build();
	}
}