package com.grahamsfault.nfl.command;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grahamsfault.nfl.StatsConfiguration;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.dao.MySQLPlayerDAO;
import com.grahamsfault.nfl.dao.PlayerDAO;
import com.grahamsfault.nfl.file.PlayerFileReader;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import javax.sql.DataSource;
import java.util.List;

public class PlayerImportCommand extends ConfiguredCommand<StatsConfiguration> {

	public PlayerImportCommand() {
		super("import-players", "Import players into the database");
	}

	@Override
	public void configure(Subparser subparser) {
		super.configure(subparser);
	}

	@Override
	protected void run(Bootstrap bootstrap, Namespace namespace, StatsConfiguration configuration) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		DataSource stats = configuration.getDataSourceFactory().build(new MetricRegistry(), "stats");
		PlayerFileReader playerFileReader = new PlayerFileReader(mapper);
		PlayerDAO playerDAO = new MySQLPlayerDAO(stats);

		List<Player> players = playerFileReader.allPlayers();
		int index = 0;
		for(Player player : players) {
			if (player.getPosition() == null) {
				System.out.println("Skipping " + player.getFullName() + " (" + ++index + " of " + players.size() + ")");
			} else {
				System.out.println("Importing " + player.getFullName() + " (" + ++index + " of " + players.size() + ")");
				playerDAO.updatePlayer(player);
			}
		}
	}
}
