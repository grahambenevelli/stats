package com.grahamsfault.nfl.stats.api.model.game.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuarterlyScores {

	private final int firstQuarterScore;
	private final int secondQuarterScore;
	private final int thirdQuarterScore;
	private final int fourthQuarterScore;
	private final int overtimeScore;
	private final int totalScore;

	@JsonCreator
	public QuarterlyScores(
			@JsonProperty("1") int firstQuarterScore,
			@JsonProperty("2") int secondQuarterScore,
			@JsonProperty("3") int thirdQuarterScore,
			@JsonProperty("4") int fourthQuarterScore,
			@JsonProperty("5") int overtimeScore,
			@JsonProperty("T") int totalScore) {
		this.firstQuarterScore = firstQuarterScore;
		this.secondQuarterScore = secondQuarterScore;
		this.thirdQuarterScore = thirdQuarterScore;
		this.fourthQuarterScore = fourthQuarterScore;
		this.overtimeScore = overtimeScore;
		this.totalScore = totalScore;
	}

	public int getFirstQuarterScore() {
		return firstQuarterScore;
	}

	public int getSecondQuarterScore() {
		return secondQuarterScore;
	}

	public int getThirdQuarterScore() {
		return thirdQuarterScore;
	}

	public int getFourthQuarterScore() {
		return fourthQuarterScore;
	}

	public int getOvertimeScore() {
		return overtimeScore;
	}

	public int getTotalScore() {
		return totalScore;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("firstQuarterScore", firstQuarterScore)
				.add("secondQuarterScore", secondQuarterScore)
				.add("thirdQuarterScore", thirdQuarterScore)
				.add("fourthQuarterScore", fourthQuarterScore)
				.add("overtimeScore", overtimeScore)
				.add("totalScore", totalScore)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof QuarterlyScores)) return false;
		QuarterlyScores that = (QuarterlyScores) o;
		return getFirstQuarterScore() == that.getFirstQuarterScore() &&
				getSecondQuarterScore() == that.getSecondQuarterScore() &&
				getThirdQuarterScore() == that.getThirdQuarterScore() &&
				getFourthQuarterScore() == that.getFourthQuarterScore() &&
				getOvertimeScore() == that.getOvertimeScore() &&
				getTotalScore() == that.getTotalScore();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getFirstQuarterScore(), getSecondQuarterScore(), getThirdQuarterScore(), getFourthQuarterScore(), getOvertimeScore(), getTotalScore());
	}
}
