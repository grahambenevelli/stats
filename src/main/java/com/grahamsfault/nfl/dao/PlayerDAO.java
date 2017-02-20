package com.grahamsfault.nfl.dao;

import com.grahamsfault.nfl.model.Player;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PlayerDAO {

	Set<Player> searchForPlayer(
			final Optional<String> firstName,
			final Optional<String> lastName
	);

}
