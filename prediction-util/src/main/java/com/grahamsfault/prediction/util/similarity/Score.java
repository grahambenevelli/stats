package com.grahamsfault.prediction.util.similarity;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Score {

	private final double score;

	public Score(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Score)) return false;
		Score score1 = (Score) o;
		return Double.compare(score1.getScore(), getScore()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getScore());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("score", score)
				.toString();
	}
}
