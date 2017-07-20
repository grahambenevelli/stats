package com.grahamsfault.nfl.command.steps;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.file.PlayerFileReader;
import com.grahamsfault.nfl.manager.PlayerManager;

import java.util.List;

/**
 * Step in the ETL process that imports the already known players, this is pulling from a file so it does not pull
 * in new players, those will have to be manually updated until we have a way to pull that from the NFL site.
 */
public class ImportPlayerStep implements EtlStep {

	private final PlayerFileReader playerFileReader;
	private final PlayerManager playerManager;

	public ImportPlayerStep(PlayerFileReader playerFileReader, PlayerManager playerManager) {
		this.playerFileReader = playerFileReader;
		this.playerManager = playerManager;
	}

	@Override
	public void run() {
		List<Player> players = playerFileReader.allPlayers();
		int index = 0;
		for(Player player : players) {
			if (player.getPosition() == null) {
				System.out.println("Skipping " + player.getFullName() + " (" + ++index + " of " + players.size() + ")");
			} else {
				System.out.println("Importing " + player.getFullName() + " (" + ++index + " of " + players.size() + ")");
				playerManager.updatePlayer(player);
			}
		}
	}
}
