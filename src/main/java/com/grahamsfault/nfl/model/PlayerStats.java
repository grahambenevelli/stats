package com.grahamsfault.nfl.model;


import com.google.common.base.MoreObjects;

import java.util.Objects;

public class PlayerStats {

	private final String name;
	private final String id;
	private final Integer passingAttempts;
	private final Integer passingCompletions;
	private final Integer passingYards;
	private final Integer passingTouchdowns;
	private final Integer interceptions;
	private final Integer rushingAttempts;
	private final Integer rushingYards;
	private final Integer rushingTouchdowns;
	private final Integer rushingLong;
	private final Integer rushingLongTouchdown;
	private final Integer receptions;
	private final Integer receivingYards;
	private final Integer receivingTouchdowns;
	private final Integer receivingLong;
	private final Integer receivingLongTouchdown;
	private final Integer fumbles;
	private final Integer fumblesLost;
	private final Integer fumblesRecovered;
	private final Integer fumbleYards;

	private PlayerStats(
			String name,
			String id,
			Integer passingAttempts,
			Integer passingCompletions,
			Integer passingYards,
			Integer passingTouchdowns,
			Integer interceptions,
			Integer rushingAttempts,
			Integer rushingYards,
			Integer rushingTouchdowns,
			Integer rushingLong,
			Integer rushingLongTouchdown,
			Integer receptions,
			Integer receivingYards,
			Integer receivingTouchdowns,
			Integer receivingLong,
			Integer receivingLongTouchdown,
			Integer fumbles,
			Integer fumblesLost,
			Integer fumblesRecovered,
			Integer fumbleYards
	) {
		this.name = name;
		this.id = id;
		this.passingAttempts = passingAttempts;
		this.passingCompletions = passingCompletions;
		this.passingYards = passingYards;
		this.passingTouchdowns = passingTouchdowns;
		this.interceptions = interceptions;
		this.rushingAttempts = rushingAttempts;
		this.rushingYards = rushingYards;
		this.rushingTouchdowns = rushingTouchdowns;
		this.rushingLong = rushingLong;
		this.rushingLongTouchdown = rushingLongTouchdown;
		this.receptions = receptions;
		this.receivingYards = receivingYards;
		this.receivingTouchdowns = receivingTouchdowns;
		this.receivingLong = receivingLong;
		this.receivingLongTouchdown = receivingLongTouchdown;
		this.fumbles = fumbles;
		this.fumblesLost = fumblesLost;
		this.fumblesRecovered = fumblesRecovered;
		this.fumbleYards = fumbleYards;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Integer getPassingAttempts() {
		return passingAttempts;
	}

	public Integer getPassingCompletions() {
		return passingCompletions;
	}

	public Integer getPassingYards() {
		return passingYards;
	}

	public Integer getPassingTouchdowns() {
		return passingTouchdowns;
	}

	public Integer getInterceptions() {
		return interceptions;
	}

	public Integer getRushingAttempts() {
		return rushingAttempts;
	}

	public Integer getRushingYards() {
		return rushingYards;
	}

	public Integer getRushingTouchdowns() {
		return rushingTouchdowns;
	}

	public Integer getRushingLong() {
		return rushingLong;
	}

	public Integer getRushingLongTouchdown() {
		return rushingLongTouchdown;
	}

	public Integer getReceptions() {
		return receptions;
	}

	public Integer getReceivingYards() {
		return receivingYards;
	}

	public Integer getReceivingTouchdowns() {
		return receivingTouchdowns;
	}

	public Integer getReceivingLong() {
		return receivingLong;
	}

	public Integer getReceivingLongTouchdown() {
		return receivingLongTouchdown;
	}

	public Integer getFumbles() {
		return fumbles;
	}

	public Integer getFumblesLost() {
		return fumblesLost;
	}

	public Integer getFumblesRecovered() {
		return fumblesRecovered;
	}

	public Integer getFumbleYards() {
		return fumbleYards;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("id", id)
				.add("passingAttempts", passingAttempts)
				.add("passingCompletions", passingCompletions)
				.add("passingYards", passingYards)
				.add("passingTouchdowns", passingTouchdowns)
				.add("interceptions", interceptions)
				.add("rushingAttempts", rushingAttempts)
				.add("rushingYards", rushingYards)
				.add("rushingTouchdowns", rushingTouchdowns)
				.add("rushingLong", rushingLong)
				.add("rushingLongTouchdown", rushingLongTouchdown)
				.add("receptions", receptions)
				.add("receivingYards", receivingYards)
				.add("receivingTouchdowns", receivingTouchdowns)
				.add("receivingLong", receivingLong)
				.add("receivingLongTouchdown", receivingLongTouchdown)
				.add("fumbles", fumbles)
				.add("fumblesLost", fumblesLost)
				.add("fumblesRecovered", fumblesRecovered)
				.add("fumbleYards", fumbleYards)
				.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PlayerStats)) return false;
		PlayerStats that = (PlayerStats) o;
		return Objects.equals(getName(), that.getName()) &&
				Objects.equals(getId(), that.getId()) &&
				Objects.equals(getPassingAttempts(), that.getPassingAttempts()) &&
				Objects.equals(getPassingCompletions(), that.getPassingCompletions()) &&
				Objects.equals(getPassingYards(), that.getPassingYards()) &&
				Objects.equals(getPassingTouchdowns(), that.getPassingTouchdowns()) &&
				Objects.equals(getInterceptions(), that.getInterceptions()) &&
				Objects.equals(getRushingAttempts(), that.getRushingAttempts()) &&
				Objects.equals(getRushingYards(), that.getRushingYards()) &&
				Objects.equals(getRushingTouchdowns(), that.getRushingTouchdowns()) &&
				Objects.equals(getRushingLong(), that.getRushingLong()) &&
				Objects.equals(getRushingLongTouchdown(), that.getRushingLongTouchdown()) &&
				Objects.equals(getReceptions(), that.getReceptions()) &&
				Objects.equals(getReceivingYards(), that.getReceivingYards()) &&
				Objects.equals(getReceivingTouchdowns(), that.getReceivingTouchdowns()) &&
				Objects.equals(getReceivingLong(), that.getReceivingLong()) &&
				Objects.equals(getReceivingLongTouchdown(), that.getReceivingLongTouchdown()) &&
				Objects.equals(getFumbles(), that.getFumbles()) &&
				Objects.equals(getFumblesLost(), that.getFumblesLost()) &&
				Objects.equals(getFumblesRecovered(), that.getFumblesRecovered()) &&
				Objects.equals(getFumbleYards(), that.getFumbleYards());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getId(), getPassingAttempts(), getPassingCompletions(), getPassingYards(), getPassingTouchdowns(), getInterceptions(), getRushingAttempts(), getRushingYards(), getRushingTouchdowns(), getRushingLong(), getRushingLongTouchdown(), getReceptions(), getReceivingYards(), getReceivingTouchdowns(), getReceivingLong(), getReceivingLongTouchdown(), getFumbles(), getFumblesLost(), getFumblesRecovered(), getFumbleYards());
	}

	public static Builder builder(String name, String id) {
		return new Builder(name, id);
	}

	public static class Builder {

		private final String name;
		private final String id;
		private Integer passingAttempts;
		private Integer passingCompletions;
		private Integer passingYards;
		private Integer passingTouchdowns;
		private Integer interceptions;
		private Integer rushingAttempts;
		private Integer rushingYards;
		private Integer rushingTouchdowns;
		private Integer rushingLong;
		private Integer rushingLongTouchdown;
		private Integer receptions;
		private Integer receivingYards;
		private Integer receivingTouchdowns;
		private Integer receivingLong;
		private Integer receivingLongTouchdown;
		private Integer fumbles;
		private Integer fumblesLost;
		private Integer fumblesRecovered;
		private Integer fumbleYards;

		public Builder(String name, String id) {
			this.name = name;
			this.id = id;
		}

		public PlayerStats build() {
			return new PlayerStats(
					name,
					id,
					passingAttempts,
					passingCompletions,
					passingYards,
					passingTouchdowns,
					interceptions,
					rushingAttempts,
					rushingYards,
					rushingTouchdowns,
					rushingLong,
					rushingLongTouchdown,
					receptions,
					receivingYards,
					receivingTouchdowns,
					receivingLong,
					receivingLongTouchdown,
					fumbles,
					fumblesLost,
					fumblesRecovered,
					fumbleYards
			);
		}

		public Builder passingAttempts(int passingAttempts) {
			this.passingAttempts = passingAttempts;
			return this;
		}

		public Builder passingCompletions(int passingCompletions) {
			this.passingCompletions = passingCompletions;
			return this;
		}

		public Builder passingYards(int passingYards) {
			this.passingYards = passingYards;
			return this;
		}

		public Builder passingTouchdowns(int passingTouchdowns) {
			this.passingTouchdowns = passingTouchdowns;
			return this;
		}

		public Builder interceptions(int interceptions) {
			this.interceptions = interceptions;
			return this;
		}

		public Builder rushingAttempts(int rushingAttempts) {
			this.rushingAttempts = rushingAttempts;
			return this;
		}

		public Builder rushingYards(int rushingYards) {
			this.rushingYards = rushingYards;
			return this;
		}

		public Builder rushingTouchdowns(int rushingTouchdowns) {
			this.rushingTouchdowns = rushingTouchdowns;
			return this;
		}

		public Builder rushingLong(int rushingLong) {
			this.rushingLong = rushingLong;
			return this;
		}

		public Builder rushingLongTouchdown(int rushingLongTouchdown) {
			this.rushingLongTouchdown = rushingLongTouchdown;
			return this;
		}

		public Builder receptions(int receptions) {
			this.receptions = receptions;
			return this;
		}

		public Builder receivingYards(int receivingYards) {
			this.receivingYards = receivingYards;
			return this;
		}

		public Builder receivingTouchdowns(int receivingTouchdowns) {
			this.receivingTouchdowns = receivingTouchdowns;
			return this;
		}

		public Builder receivingLong(int receivingLong) {
			this.receivingLong = receivingLong;
			return this;
		}

		public Builder receivingLongTouchdown(int receivingLongTouchdown) {
			this.receivingLongTouchdown = receivingLongTouchdown;
			return this;
		}

		public Builder fumbles(int fumbles) {
			this.fumbles = fumbles;
			return this;
		}

		public Builder fumblesLost(int fumblesLost) {
			this.fumblesLost = fumblesLost;
			return this;
		}

		public Builder fumblesRecovered(int fumblesRecovered) {
			this.fumblesRecovered = fumblesRecovered;
			return this;
		}

		public Builder fumbleYards(int fumbleYards) {
			this.fumbleYards = fumbleYards;
			return this;
		}
	}
}
