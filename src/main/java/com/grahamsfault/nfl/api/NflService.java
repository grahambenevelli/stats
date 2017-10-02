package com.grahamsfault.nfl.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.grahamsfault.nfl.api.exception.NflServiceException;
import com.grahamsfault.nfl.api.exception.NflServicePlayerDataException;
import com.grahamsfault.nfl.api.model.Game;
import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.game.GameStatsWrapper;
import com.grahamsfault.nfl.api.model.game.GameType;
import com.grahamsfault.nfl.api.model.player.Position;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for talking to NFL.com
 */
public class NflService {

	private static final String DEFAULT_FILENAME = "assets/db/schedule.json";

	private final ObjectMapper mapper;
	private final Client client;

	public NflService(ObjectMapper mapper) {
		this.mapper = mapper;
		// TODO pass this in
		client = ClientBuilder.newClient();
	}

	/**
	 * Search games given the current  paramters
	 *
	 * @param year The year of the game
	 * @param gameType The type of the game
	 * @param week The week of the game
	 * @param home The home team
	 * @param away The away team
	 * @return The list of games that match the search parameters
	 */
	public List<Game> searchGames(
			int year,
			GameType gameType,
			Optional<Integer> week,
			Optional<Team> home,
			Optional<Team> away
	) {
		List<Game> allGames = allGames();

		return allGames.stream()
				.filter(game -> game.getYear() == year)
				.filter(game -> game.getSeasonType() == gameType)
				.filter(game -> !week.isPresent() || game.getWeek() == week.get())
				.filter(game -> !home.isPresent() || game.getHome() == home.get())
				.filter(game -> !away.isPresent() || game.getAway() == away.get())
				.collect(Collectors.toList());
	}

	/**
	 * Get the game data based on eid
	 *
	 * @param eid The game id
	 * @return The Game Stats
	 */
	public GameStatsWrapper gameStats(String eid) {
		WebTarget target = client.target("http://www.nfl.com")
				.path(MessageFormat.format("/liveupdate/game-center/{0}/{0}_gtd.json", eid));

		Response response = target.request().get();
		GameStatsWrapper gameStats = response.readEntity(new GenericType<GameStatsWrapper>() {});

		return gameStats;
	}

	/**
	 * Get all the games
	 */
	protected List<Game> allGames() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(DEFAULT_FILENAME).getFile());
		try {
			return mapper.readValue(file, new TypeReference<List<Game>>() {});
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * Get the player info, including name, position, height, weight, etc.
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
