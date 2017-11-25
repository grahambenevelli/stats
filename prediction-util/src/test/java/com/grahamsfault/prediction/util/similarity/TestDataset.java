package com.grahamsfault.prediction.util.similarity;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public abstract class TestDataset {

	public static final String LISA_ROSE = "Lisa Rose";
	public static final String GENE_SEYMOUR = "Gene Seymour";
	public static final String MICHAEL_PHILLIPS = "Michael Phillips";
	public static final String CLAUDIA_PUIG = "Claudia Puig";
	public static final String MICK_LA_SALLE = "Mick LaSalle";
	public static final String JACK_MATTHEWS = "Jack Matthews";
	public static final String TOBY = "Toby";

	public static final String LADY_IN_THE_WATER = "Lady in the Water";
	public static final String SNAKES_ON_A_PLANE = "Snakes on a Plane";
	public static final String JUST_MY_LUCK = "Just My Luck";
	public static final String SUPERMAN_RETURNS = "Superman Returns";
	public static final String YOU_ME_AND_DUPREE = "You, Me and Dupree";
	public static final String THE_NIGHT_LISTENER = "The Night Listener";

	public static Map<String, Map<String, Double>> getDataset() {
		return ImmutableMap.<String, Map<String, Double>>builder()
				.put(
						LISA_ROSE,
						ImmutableMap.<String, Double>builder()
								.put(LADY_IN_THE_WATER, 2.5)
								.put(SNAKES_ON_A_PLANE, 3.5)
								.put(JUST_MY_LUCK, 3.0)
								.put(SUPERMAN_RETURNS, 3.5)
								.put(YOU_ME_AND_DUPREE, 2.5)
								.put(THE_NIGHT_LISTENER, 3.0)
								.build()
				)
				.put(
						GENE_SEYMOUR,
						ImmutableMap.<String, Double>builder()
								.put(LADY_IN_THE_WATER, 3.0)
								.put(SNAKES_ON_A_PLANE, 3.5)
								.put(JUST_MY_LUCK, 1.5)
								.put(SUPERMAN_RETURNS, 5.0)
								.put(YOU_ME_AND_DUPREE, 3.5)
								.put(THE_NIGHT_LISTENER, 3.0)
								.build()
				)
				.put(
						MICHAEL_PHILLIPS,
						ImmutableMap.<String, Double>builder()
								.put(LADY_IN_THE_WATER, 2.5)
								.put(SNAKES_ON_A_PLANE, 3.0)
								.put(SUPERMAN_RETURNS, 3.5)
								.put(THE_NIGHT_LISTENER, 4.0)
								.build()
				)
				.put(
						CLAUDIA_PUIG,
						ImmutableMap.<String, Double>builder()
								.put(SNAKES_ON_A_PLANE, 3.5)
								.put(JUST_MY_LUCK, 3.0)
								.put(SUPERMAN_RETURNS, 4.0)
								.put(YOU_ME_AND_DUPREE, 2.5)
								.put(THE_NIGHT_LISTENER, 4.5)
								.build()
				)
				.put(
						MICK_LA_SALLE,
						ImmutableMap.<String, Double>builder()
								.put(LADY_IN_THE_WATER, 3.0)
								.put(SNAKES_ON_A_PLANE, 4.0)
								.put(JUST_MY_LUCK, 2.0)
								.put(SUPERMAN_RETURNS, 3.0)
								.put(YOU_ME_AND_DUPREE, 2.0)
								.put(THE_NIGHT_LISTENER, 3.0)
								.build()
				)
				.put(
						JACK_MATTHEWS,
						ImmutableMap.<String, Double>builder()
								.put(LADY_IN_THE_WATER, 3.0)
								.put(SNAKES_ON_A_PLANE, 4.0)
								.put(SUPERMAN_RETURNS, 5.0)
								.put(YOU_ME_AND_DUPREE, 3.5)
								.put(THE_NIGHT_LISTENER, 3.0)
								.build()
				)
				.put(
						TOBY,
						ImmutableMap.<String, Double>builder()
								.put(SNAKES_ON_A_PLANE, 4.5)
								.put(SUPERMAN_RETURNS, 4.0)
								.put(YOU_ME_AND_DUPREE, 1.0)
								.build()
				)
				.build();
	}
}