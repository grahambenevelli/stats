package com.grahamsfault.stats.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grahamsfault.nfl.api.model.Player;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NClosestResults {

	private final int n;
	private final List<Unit> closest;
	private final Player player;

	@JsonCreator
	public NClosestResults(int n, List<Unit> closest, Player player) {
		this.n = n;
		this.closest = closest;
		this.player = player;
	}

	public static Unit unit(Player player, int year, PlayerStats playerStats, double correlation) {
		return new Unit(player, year, playerStats, correlation);
	}

	public int getN() {
		return n;
	}

	public List<Unit> getClosest() {
		return closest;
	}

	public Player getPlayer() {
		return player;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Unit {
		private final Player player;
		private final int year;
		private final PlayerStats playerStats;
		private final double correlation;

		public Unit(Player player, int year, PlayerStats playerStats, double correlation) {
			this.player = player;
			this.year = year;
			this.playerStats = playerStats;
			this.correlation = correlation;
		}

		public Player getPlayer() {
			return player;
		}

		public int getYear() {
			return year;
		}

		public PlayerStats getPlayerStats() {
			return playerStats;
		}

		public double getCorrelation() {
			return correlation;
		}
	}
}