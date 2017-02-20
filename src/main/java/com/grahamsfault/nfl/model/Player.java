package com.grahamsfault.nfl.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
	private final Integer yearsPro;
	private final Integer number;
	private final String status;
	private final Team team;
	private final Position position;

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
			@JsonProperty("years_pro") Integer yearsPro,
			@JsonProperty("number") Integer number,
			@JsonProperty("status") String status,
			@JsonProperty("team") Team team,
			@JsonProperty("position") Position position) {
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
		this.yearsPro = yearsPro;
		this.number = number;
		this.status = status;
		this.team = team;
		this.position = position;
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

	public Integer getYearsPro() {
		return yearsPro;
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
				Objects.equals(getYearsPro(), player.getYearsPro()) &&
				Objects.equals(getNumber(), player.getNumber()) &&
				Objects.equals(getStatus(), player.getStatus()) &&
				getTeam() == player.getTeam() &&
				getPosition() == player.getPosition();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getBirthdate(), getCollege(), getFirstName(), getLastName(), getFullName(), getGsisId(), getGsisName(), getProfileId(), getProfileUrl(), getHeight(), getWeight(), getYearsPro(), getNumber(), getStatus(), getTeam(), getPosition());
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
				.add("yearsPro", yearsPro)
				.add("number", number)
				.add("status", status)
				.add("team", team)
				.add("position", position)
				.toString();
	}
}
