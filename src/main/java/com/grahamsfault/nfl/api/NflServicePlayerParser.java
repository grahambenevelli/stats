package com.grahamsfault.nfl.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.grahamsfault.nfl.api.exception.NflServiceException;
import com.grahamsfault.nfl.api.exception.NflServicePlayerDataException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parse the nfl player page
 */
public class NflServicePlayerParser {

	private final Elements playerProfile;
	private final Element playerNameElement;
	private final Element playerNumberRow;
	private String heightRow;
	private String weightRow;
	private String birthRow;
	private String collegeRow;
	private String experienceRow;
	private String highSchoolRow;

	public NflServicePlayerParser(Document doc) throws NflServicePlayerDataException {
		this.playerProfile = doc.select("#player-profile");
		this.playerNameElement = playerProfile.select(".player-name").first();
		this.playerNumberRow = playerProfile.select(".player-number").first();

		Element playerInfo = playerProfile.select(".player-info").first();
		if (playerInfo == null) {
			throw new NflServicePlayerDataException("Player not found");
		}
		playerInfo.select("p").forEach(this::determineRow);
	}

	private void determineRow(Element element) {
		if (!element.select(".player-number").isEmpty()) {
			return;
		}

		if (!element.select(".player-info").isEmpty()) {
			return;
		}

		if (element.text().contains("Height")) {
			this.heightRow = element.text();
		}

		if (element.text().contains("Weight")) {
			this.weightRow = element.text();
		}

		if (element.text().contains("Born")) {
			this.birthRow = element.text();
		}

		if (element.text().contains("College")) {
			this.collegeRow = element.text();
		}

		if (element.text().contains("Experience")) {
			this.experienceRow = element.text();
		}

		if (element.text().contains("High School")) {
			this.highSchoolRow = element.text();
		}
	}

	/**
	 * Get the first name of the player
	 *
	 * @return The first name of the player
	 * @throws NflServiceException
	 */
	public Optional<String> getFirstName() throws NflServiceException {
		if (playerNameElement == null) {
			return Optional.empty();
		}
		String playerNameStr = playerNameElement.text();
		String[] split = playerNameStr.split(" ");
		return Lists.newArrayList(split).stream()
				.findFirst()
				.map(this::trim);
	}

	/**
	 * Get the last name of the player
	 *
	 * @return The last name of the player
	 * @throws NflServiceException
	 */
	public Optional<String> getLastName() throws NflServiceException {
		if (playerNameElement == null) {
			return Optional.empty();
		}

		String playerNameStr = playerNameElement.text();
		String[] split = playerNameStr.split(" ");
		return Lists.newArrayList(split).stream()
				.reduce((first, second) -> second)
				.map(this::trim);
	}

	/**
	 * Get the jersey number
	 *
	 * @return The jersey number of the player
	 * @throws NflServicePlayerDataException
	 */
	public Optional<Integer> getJerseyNumber() throws NflServicePlayerDataException {
		if (playerNumberRow == null) {
			return Optional.empty();
		}

		String text = playerNumberRow.text();
		String[] split = text.split(" ");
		Optional<String> number = Lists.newArrayList(split).stream().findFirst();

		if (number.isPresent()) {
			try {
				if (number.get().charAt(0) == '#') {
					return Optional.of(Integer.parseInt(number.get().substring(1)));
				} else {
					return Optional.of(Integer.parseInt(number.get()));
				}
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	/**
	 * The position of the player
	 *
	 * @return The position of the player
	 * @throws NflServicePlayerDataException
	 */
	public Optional<String> getPosition() throws NflServicePlayerDataException {
		if (playerNumberRow == null) {
			return Optional.empty();
		}

		String[] split = playerNumberRow.text().split(" ");
		return Lists.newArrayList(split).stream().reduce((first, second) -> second);
	}

	/**
	 * Get the height of the player
	 *
	 * @return The hight of the player
	 */
	public Optional<Integer> getHeight() {
		if (heightRow == null) {
			return Optional.empty();
		}

		Pattern p = Pattern.compile(".*Height: (\\d+-\\d+).*");
		Matcher m = p.matcher(heightRow);
		if (m.find()) {
			String s = m.group(1);
			String[] split = s.split("-");
			return Optional.of(
					Integer.parseInt(split[0]) * 12 + Integer.parseInt(split[1])
			);
		}

		return Optional.empty();
	}

	/**
	 * Get the weight of the player
	 *
	 * @return The weight of the player
	 */
	public Optional<Integer> getWeight() {
		if (weightRow == null) {
			return Optional.empty();
		}

		Pattern p = Pattern.compile(".*Weight: (\\d+).*");
		Matcher m = p.matcher(heightRow);
		if (m.find()) {
			String s = m.group(1);
			return Optional.of(
					Integer.parseInt(s)
			);
		}

		return Optional.empty();
	}

	/**
	 * Get the date of birth of the player
	 *
	 * @return The date of birth of the player
	 */
	public Optional<String> getDateOrBirth() {
		if (birthRow == null) {
			return Optional.empty();
		}

		String[] split = birthRow.split(" ");
		return Optional.of(trim(split[1]));
	}

	/**
	 * Get the college of the player
	 *
	 * @return The college of the player
	 */
	public Optional<String> getCollege() {
		if (collegeRow == null) {
			return Optional.empty();
		}

		return Optional.of(
				collegeRow.substring(9)
		);
	}

	/**
	 * Get the experience of the player
	 *
	 * @return The years of experience
	 */
	public Optional<Integer> getExperience() {
		if (experienceRow == null) {
			return Optional.empty();
		}

		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(experienceRow);
		if (m.find()) {
			return Optional.of(
					Integer.parseInt(m.group())
			);
		}

		return Optional.empty();
	}

	/**
	 * The high school the player went to
	 *
	 * @return The high school of the player.
	 */
	public Optional<String> getHighSchool() {
		if (highSchoolRow == null || highSchoolRow.length() < 13) {
			return Optional.empty();
		}

		return Optional.of(
				trim(highSchoolRow.substring(13))
		);
	}

	/**
	 * Trim the given string of whitespace
	 *
	 * @param toTrim The string to trim
	 * @return The trimmed string
	 */
	private String trim(String toTrim) {
		return toTrim.replace((char) 160, ' ').trim();
	}

	/**
	 * Get the text of the row in the layer info
	 *
	 * @param row The number of the row
	 * @return The text of the row
	 */
	private String getRowText(int row) {
		Element playerInfo = playerProfile.select(".player-info").first();
		Element heightWeightLine = playerInfo.child(row);
		return heightWeightLine.text();
	}
}
