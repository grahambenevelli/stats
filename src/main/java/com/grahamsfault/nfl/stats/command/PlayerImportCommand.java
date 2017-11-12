package com.grahamsfault.nfl.stats.command;

import com.google.common.collect.ImmutableList;
import com.grahamsfault.nfl.stats.StatsConfiguration;
import com.grahamsfault.nfl.stats.command.steps.EtlStep;

import java.util.List;

/**
 * A command for directly importing players into the system.
 */
public class PlayerImportCommand extends StepCommand {

	public PlayerImportCommand() {
		super("import-players", "Import players into the database");
	}

	@Override
	public List<EtlStep> steps(StatsConfiguration configuration) {
		return ImmutableList.<EtlStep>builder()
				.add(getImportPlayerStep(configuration))
				.build();
	}
}
