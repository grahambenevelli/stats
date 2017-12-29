package com.grahamsfault.prediction.util;

import com.google.common.base.MoreObjects;

public class Node<T extends Number> {

	private final T value;

	private Node(T value) {
		this.value = value;
	}

	public static <A extends Number> Node<A> of(A value) {
		return new Node<>(value);
	}

	public T getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Node)) return false;
		Node<?> node = (Node<?>) o;
		return com.google.common.base.Objects.equal(getValue(), node.getValue());
	}

	@Override
	public int hashCode() {
		return com.google.common.base.Objects.hashCode(getValue());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("value", value)
				.toString();
	}
}
