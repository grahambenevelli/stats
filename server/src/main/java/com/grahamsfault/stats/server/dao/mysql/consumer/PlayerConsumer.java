package com.grahamsfault.stats.server.dao.mysql.consumer;

import com.grahamsfault.nfl.api.model.Player;
import com.grahamsfault.nfl.api.model.Team;
import com.grahamsfault.nfl.api.model.player.Position;

import java.sql.SQLException;

/**
 * Class for consuming a Player object from a ResultSet
 */
public class PlayerConsumer extends AbstractConsumer<Player> {

	private String birthdate;
	private String college;
	private String firstName;
	private String lastName;
	private String fullName;
	private String gsisId;
	private String gsisName;
	private String profileId;
	private String profileUrl;
	private String height;
	private String weight;
	private String number;
	private String status;
	private String team;
	private String position;
	private String experience;

	private PlayerConsumer() {
		this.birthdate = "birthdate";
		this.college = "college";
		this.firstName = "first_name";
		this.lastName = "last_name";
		this.fullName = "full_name";
		this.gsisId = "gsis_id";
		this.gsisName = "gsis_name";
		this.profileId = "profile_id";
		this.profileUrl = "profile_url";
		this.height = "height";
		this.weight = "weight";
		this.number = "number";
		this.status = "status";
		this.team = "team";
		this.position = "position";
		this.experience = "experience";
	}

	public static PlayerConsumer consumer() {
		return new PlayerConsumer();
	}

	public PlayerConsumer setBirthdate(String birthdate) {
		this.birthdate = birthdate;
		return this;
	}

	public PlayerConsumer setCollege(String college) {
		this.college = college;
		return this;
	}

	public PlayerConsumer setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public PlayerConsumer setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public PlayerConsumer setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public PlayerConsumer setGsisId(String gsisId) {
		this.gsisId = gsisId;
		return this;
	}

	public PlayerConsumer setGsisName(String gsisName) {
		this.gsisName = gsisName;
		return this;
	}

	public PlayerConsumer setProfileId(String profileId) {
		this.profileId = profileId;
		return this;
	}

	public PlayerConsumer setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
		return this;
	}

	public PlayerConsumer setHeight(String height) {
		this.height = height;
		return this;
	}

	public PlayerConsumer setWeight(String weight) {
		this.weight = weight;
		return this;
	}

	public PlayerConsumer setNumber(String number) {
		this.number = number;
		return this;
	}

	public PlayerConsumer setStatus(String status) {
		this.status = status;
		return this;
	}

	public PlayerConsumer setTeam(String team) {
		this.team = team;
		return this;
	}

	public PlayerConsumer setPosition(String position) {
		this.position = position;
		return this;
	}

	public PlayerConsumer setExperience(String experience) {
		this.experience = experience;
		return this;
	}

	@Override
	public Player consume(ReadOnlyResultSet result) throws SQLException {
		return new Player(
				result.getString(birthdate),
				result.getString(college),
				result.getString(firstName),
				result.getString(lastName),
				result.getString(fullName),
				result.getString(gsisId),
				result.getString(gsisName),
				result.getLong(profileId),
				result.getURL(profileUrl),
				result.getInt(height),
				result.getInt(weight),
				result.getInt(number),
				result.getString(status),
				Team.forValue(result.getString(team)),
				Position.forValue(result.getString(position)),
				null,
				result.getInt(experience),
				null
		);
	}
}