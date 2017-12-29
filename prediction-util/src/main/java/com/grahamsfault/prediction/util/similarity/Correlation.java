package com.grahamsfault.prediction.util.similarity;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Correlation {

	private final double value;

	private Correlation(double value) {
		this.value = value;
	}

	public static Correlation of(double value) {
		return new Correlation(value);
	}

	public double getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Correlation)) return false;
		Correlation correlation1 = (Correlation) o;
		return Double.compare(correlation1.getValue(), getValue()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValue());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("value", value)
				.toString();
	}
}
