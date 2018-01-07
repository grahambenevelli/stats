package com.grahamsfault.stats.server.api;

import com.grahamsfault.stats.server.api.exception.NflServiceException;
import com.grahamsfault.stats.server.api.exception.NflServiceGameDataException;
import com.grahamsfault.stats.server.api.exception.NflServicePlayerDataException;
import com.grahamsfault.stats.server.api.model.Player;
import com.grahamsfault.stats.server.api.model.game.GameNotes;
import com.grahamsfault.stats.server.api.model.game.GameStatsWrapper;
import com.grahamsfault.stats.server.api.model.player.Position;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * Service for talking to NFL.com
 */
public class NflService {

	private final Client client;

	public NflService(Client client) {
		this.client = client;
	}

	/**
	 * Get the game data based on eid
	 *
	 * @param eid The game id
	 * @return The Game Stats
	 */
	private GameStatsWrapper gameStats(String eid) {
		WebTarget target = client.target("http://www.nfl.com")
				.path(MessageFormat.format("/liveupdate/game-center/{0}/{0}_gtd.json", eid));

		Response response = target.request().get();
		return response.readEntity(new GenericType<GameStatsWrapper>() {});
	}

	/**
	 * Get the game notes for the given eid
	 *
	 * @param eid The id of the game
	 * @return The Game notes of the matching game
	 * @throws NflServiceException
	 */
	public GameNotes getGameNotes(String eid) throws NflServiceException {
		GameStatsWrapper wrapper = this.gameStats(eid);
		if(!wrapper.getProfiles().containsKey(eid)) {
			throw new NflServiceGameDataException("No games found for eid: " + eid);
		}

		return wrapper.getProfiles().get(eid);
	}

	/**
	 * Get the player info, including name, position, height, weight, etc.
	 * example: http://www.nfl.com/players/profile?id=00-0026035
	 *
	 * @param playerId The id of hte player
	 * @return The player's basic info
	 * @throws IOException
	 * @throws NflServiceException
	 */
	public Player getPlayerBasicInfo(String playerId) throws IOException, NflServiceException {
		String url = "http://www.nfl.com/players/profile?id=" + playerId;
		Document doc = Jsoup.connect(url).get();
		NflServicePlayerParser parser = new NflServicePlayerParser(doc);

		Optional<String> firstName = parser.getFirstName();
		Optional<String> lastName = parser.getLastName();

		if (!firstName.isPresent()) {
			throw new NflServicePlayerDataException("First name not present on player");
		}

		if (!lastName.isPresent()) {
			throw new NflServicePlayerDataException("First name not present on player");
		}

		Player.Builder builder = Player.builder(
				playerId,
				firstName.get(),
				lastName.get()
		);

		builder.setProfileUrl(new URL(url));
		parser.getJerseyNumber().ifPresent(builder::setNumber);
		parser.getPosition().ifPresent((p) -> builder.setPosition(Position.forValue(p)));
		parser.getHeight().ifPresent(builder::setHeight);
		parser.getWeight().ifPresent(builder::setWeight);
		parser.getDateOrBirth().ifPresent(builder::setBirthdate);
		parser.getCollege().ifPresent(builder::setCollege);
		parser.getExperience().ifPresent(builder::setExperience);
		parser.getHighSchool().ifPresent(builder::setHighSchool);

		return builder.build();
	}
}
