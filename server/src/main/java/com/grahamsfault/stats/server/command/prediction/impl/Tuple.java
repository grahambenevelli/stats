package com.grahamsfault.stats.server.command.prediction.impl;

public class Tuple<FIRST, SECOND> {

	private final FIRST first;
	private final SECOND second;

	public Tuple(FIRST first, SECOND second) {
		this.first = first;
		this.second = second;
	}

	public static <FIRST, SECOND> Tuple<FIRST, SECOND> of(FIRST first, SECOND second) {
		return new Tuple<>(first, second);
	}
}