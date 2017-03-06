package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.api.model.Player;

import java.util.Optional;
import java.util.Set;

public interface PlayerDAO {

	Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	);

}
