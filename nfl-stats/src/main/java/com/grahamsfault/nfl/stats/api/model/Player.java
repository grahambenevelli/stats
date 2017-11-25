package com.grahamsfault.nfl.stats.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.grahamsfault.nfl.stats.api.model.player.Position;

import java.net.URL;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {

	private final String birthdate;
	private final String college;
	private final String firstName;
	private final String lastName;
	private final String fullName;
	private final String gsisId;
	private final String gsisName;
	private final Long profileId;
	private final URL profileUrl;
	private final Integer height;
	private final Integer weight;
	private final Integer number;
	private final String status;
	private final Team team;
	private final Position position;
	private final String placeOfBirth;
	private final Integer experience;
	private final String highSchool;

	@JsonCreator
	public Player(
			@JsonProperty("birthdate") String birthdate,
			@JsonProperty("college") String college,
			@JsonProperty("first_name") String firstName,
			@JsonProperty("last_name") String lastName,
			@JsonProperty("full_name") String fullName,
			@JsonProperty("gsis_id") String gsisId,
			@JsonProperty("gsis_name") String gsisName,
			@JsonProperty("profile_id") Long profileId,
			@JsonProperty("profile_url") URL profileUrl,
			@JsonProperty("height") Integer height,
			@JsonProperty("weight") Integer weight,
			@JsonProperty("number") Integer number,
			@JsonProperty("status") String status,
			@JsonProperty("team") Team team,
			@JsonProperty("position") Position position,
			@JsonProperty("place_of_birth") String placeOfBirth,
			@JsonProperty("experience") Integer experience,
			@JsonProperty("high_school") String highSchool) {
		this.birthdate = birthdate;
		this.college = college;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.gsisId = gsisId;
		this.gsisName = gsisName;
		this.profileId = profileId;
		this.profileUrl = profileUrl;
		this.height = height;
		this.weight = weight;
		this.number = number;
		this.status = status;
		this.team = team;
		this.position = position;
		this.placeOfBirth = placeOfBirth;
		this.experience = experience;
		this.highSchool = highSchool;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public String getCollege() {
		return college;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getGsisId() {
		return gsisId;
	}

	public String getGsisName() {
		return gsisName;
	}

	public Long getProfileId() {
		return profileId;
	}

	public URL getProfileUrl() {
		return profileUrl;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWeight() {
		return weight;
	}

	public Integer getNumber() {
		return number;
	}

	public String getStatus() {
		return status;
	}

	public Team getTeam() {
		return team;
	}

	public Position getPosition() {
		return position;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public Integer getExperience() {
		return experience;
	}

	public static Builder builder(String gsisId, String firstName, String lastName) {
		return new Builder(gsisId, firstName, lastName);
	}

	public String getHighSchool() {
		return highSchool;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Player)) return false;
		Player player = (Player) o;
		return Objects.equals(getBirthdate(), player.getBirthdate()) &&
				Objects.equals(getCollege(), player.getCollege()) &&
				Objects.equals(getFirstName(), player.getFirstName()) &&
				Objects.equals(getLastName(), player.getLastName()) &&
				Objects.equals(getFullName(), player.getFullName()) &&
				Objects.equals(getGsisId(), player.getGsisId()) &&
				Objects.equals(getGsisName(), player.getGsisName()) &&
				Objects.equals(getProfileId(), player.getProfileId()) &&
				Objects.equals(getProfileUrl(), player.getProfileUrl()) &&
				Objects.equals(getHeight(), player.getHeight()) &&
				Objects.equals(getWeight(), player.getWeight()) &&
				Objects.equals(getNumber(), player.getNumber()) &&
				Objects.equals(getStatus(), player.getStatus()) &&
				getTeam() == player.getTeam() &&
				getPosition() == player.getPosition() &&
				Objects.equals(getPlaceOfBirth(), player.getPlaceOfBirth()) &&
				Objects.equals(getExperience(), player.getExperience()) &&
				Objects.equals(getHighSchool(), player.getHighSchool());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBirthdate(), getCollege(), getFirstName(), getLastName(), getFullName(), getGsisId(), getGsisName(), getProfileId(), getProfileUrl(), getHeight(), getWeight(), getNumber(), getStatus(), getTeam(), getPosition(), getPlaceOfBirth(), getExperience(), getHighSchool());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("birthdate", birthdate)
				.add("college", college)
				.add("firstName", firstName)
				.add("lastName", lastName)
				.add("fullName", fullName)
				.add("gsisId", gsisId)
				.add("gsisName", gsisName)
				.add("profileId", profileId)
				.add("profileUrl", profileUrl)
				.add("height", height)
				.add("weight", weight)
				.add("number", number)
				.add("status", status)
				.add("team", team)
				.add("position", position)
				.add("placeOfBirth", placeOfBirth)
				.add("experience", experience)
				.add("highSchool", highSchool)
				.toString();
	}

	public static class Builder {

		private final String firstName;
		private final String lastName;
		private String fullName;
		private String birthdate;
		private String college;
		private final String gsisId;
		private String gsisName;
		private Long profileId;
		private URL profileUrl;
		private Integer height;
		private Integer weight;
		private Integer number;
		private String status;
		private Team team;
		private Position position;
		private String placeOfBirth;
		private Integer experience;
		private String highSchool;

		private Builder(String gsisId, String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.fullName = firstName + " " + lastName;
			this.gsisId = gsisId;
			this.team = Team.FREE_AGENT;
		}

		public Player build() {
			return new Player(
					birthdate,
					college,
					firstName,
					lastName,
					fullName,
					gsisId,
					gsisName,
					profileId,
					profileUrl,
					height,
					weight,
					number,
					status,
					team,
					position,
					placeOfBirth,
					experience,
					highSchool);
		}

		public Builder setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public Builder setBirthdate(String birthdate) {
			this.birthdate = birthdate;
			return this;
		}

		public Builder setCollege(String college) {
			this.college = college;
			return this;
		}

		public Builder setGsisName(String gsisName) {
			this.gsisName = gsisName;
			return this;
		}

		public Builder setProfileId(Long profileId) {
			this.profileId = profileId;
			return this;
		}

		public Builder setProfileUrl(URL profileUrl) {
			this.profileUrl = profileUrl;
			return this;
		}

		public Builder setHeight(Integer height) {
			this.height = height;
			return this;
		}

		public Builder setWeight(Integer weight) {
			this.weight = weight;
			return this;
		}

		public Builder setNumber(Integer number) {
			this.number = number;
			return this;
		}

		public Builder setStatus(String status) {
			this.status = status;
			return this;
		}

		public Builder setTeam(Team team) {
			this.team = team;
			return this;
		}

		public Builder setPosition(Position position) {
			this.position = position;
			return this;
		}

		public Builder setPlaceOfBirth(String placeOfBirth) {
			this.placeOfBirth = placeOfBirth;
			return this;
		}

		public Builder setExperience(Integer experience) {
			this.experience = experience;
			return this;
		}

		public Builder setHighSchool(String highSchool) {
			this.highSchool = highSchool;
			return this;
		}
	}
}
