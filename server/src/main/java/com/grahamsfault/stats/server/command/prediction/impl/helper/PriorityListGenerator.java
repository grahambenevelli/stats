package com.grahamsfault.stats.server.command.prediction.impl.helper;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PriorityListGenerator<T> {

	private final int size;
	private List<PriorityNode<T>> list;

	public PriorityListGenerator(int size) {
		this.size = size;
		this.list = Lists.newArrayList();
	}

	public void add(double value, T item) {
		if (list.size() < size) {
			add(new PriorityNode<>(item, value));
			return;
		}

		PriorityNode last = list.get(list.size() - 1);
		if (last.value > value) {
			return;
		}

		add(new PriorityNode<>(item, value));
	}

	private void add(PriorityNode<T> tPriorityNode) {
		list.add(tPriorityNode);
		sortAndTrim();
	}

	private void sortAndTrim() {
		Collections.sort(list, new Comparator<PriorityNode>() {
			@Override
			public int compare(PriorityNode o1, PriorityNode o2) {
				return (int) ((o2.value - o1.value) * 1000);
			}
		});

		list = list.stream()
				.limit(size)
				.collect(Collectors.toList());
	}

	public List<T> build() {
		return list.stream()
				.map(tPriorityNode -> tPriorityNode.item)
				.collect(Collectors.toList());
	}

	private class PriorityNode<T> implements Comparable {
		public final T item;
		public final double value;

		public PriorityNode(T item, double value) {
			this.item = item;
			this.value = value;
		}

		@Override
		public int compareTo(Object o) {
			if (o instanceof PriorityNode) {
				PriorityNode other = (PriorityNode) o;
				return (int) (this.value - other.value);
			}

			throw new IllegalArgumentException();
		}
	}
}