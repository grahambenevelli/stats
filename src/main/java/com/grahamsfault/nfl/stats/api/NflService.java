package com.grahamsfault.nfl.stats.api;

import com.grahamsfault.nfl.stats.api.exception.NflServiceException;
import com.grahamsfault.nfl.stats.api.exception.NflServicePlayerDataException;
import com.grahamsfault.nfl.stats.api.model.Player;
import com.grahamsfault.nfl.stats.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.stats.api.model.player.Position;
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
	 * TODO tests
	 */
	public GameStatsWrapper gameStats(String eid) {
		WebTarget target = client.target("http://www.nfl.com")
				.path(MessageFormat.format("/liveupdate/game-center/{0}/{0}_gtd.json", eid));

		Response response = target.request().get();
		return response.readEntity(new GenericType<GameStatsWrapper>() {});
	}

	/**
	 * Get the player info, including name, position, height, weight, etc.
	 * example: http://www.nfl.com/players/profile?id=00-0026035
	 *
	 * @param playerId The id of hte player
	 * @return The player's basic info
	 * @throws IOException
	 * @throws NflServiceException
	 * TODO write tests for this
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
