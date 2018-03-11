package com.grahamsfault.stats.server.command.prediction;

import com.grahamsfault.nfl.api.model.player.Position;
import com.grahamsfault.stats.server.command.prediction.model.AccuracyStats;
import com.grahamsfault.stats.server.model.PlayerStats;
import org.testng.annotations.Test;

public class PredictionResultsTest {

	@Test
	public void testPredictionStatsSingleInstanceAllZeros() {
		PredictionResults.Builder builder = PredictionResults.builder();

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.RB,
				PlayerStats.builder("Jamaal Charles", "1002")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.WR,
				PlayerStats.builder("Roy Williams", "1003")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.TE,
				PlayerStats.builder("JerMichael Finley", "1004")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		PredictionResults results = builder.build();

		StatsAssertions.statsAssertions(results.getQbStats())
				.verify();

		StatsAssertions.statsAssertions(results.getRbStats())
				.verify();

		StatsAssertions.statsAssertions(results.getWrStats())
				.verify();

		StatsAssertions.statsAssertions(results.getTeStats())
				.verify();
	}

	@Test
	public void testPredictionStats() {
		PredictionResults.Builder builder = PredictionResults.builder();

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.RB,
				PlayerStats.builder("Jamaal Charles", "1002")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.WR,
				PlayerStats.builder("Roy Williams", "1003")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.TE,
				PlayerStats.builder("JerMichael Finley", "1004")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		PredictionResults results = builder.build();

		StatsAssertions.statsAssertions(results.getQbStats())
				.setPassingAttempts(5)
				.setPassingCompletions(6)
				.setPassingYards(7)
				.setPassingTouchdowns(8)
				.setRushingAttempts(9)
				.setRushingYards(10)
				.setRushingTouchdowns(11)
				.setRushingLong(12)
				.setRushingLongTouchdown(13)
				.setReceptions(14)
				.setReceivingYards(15)
				.setReceivingTouchdowns(16)
				.setReceivingLong(17)
				.setReceivingLongTouchdown(18)
				.setFumbles(19)
				.setFumblesLost(20)
				.setFumblesRecovered(21)
				.setFumbleYards(22)
				.verify();

		StatsAssertions.statsAssertions(results.getRbStats())
				.setPassingAttempts(5)
				.setPassingCompletions(6)
				.setPassingYards(7)
				.setPassingTouchdowns(8)
				.setRushingAttempts(9)
				.setRushingYards(10)
				.setRushingTouchdowns(11)
				.setRushingLong(12)
				.setRushingLongTouchdown(13)
				.setReceptions(14)
				.setReceivingYards(15)
				.setReceivingTouchdowns(16)
				.setReceivingLong(17)
				.setReceivingLongTouchdown(18)
				.setFumbles(19)
				.setFumblesLost(20)
				.setFumblesRecovered(21)
				.setFumbleYards(22)
				.verify();

		StatsAssertions.statsAssertions(results.getWrStats())
				.setPassingAttempts(5)
				.setPassingCompletions(6)
				.setPassingYards(7)
				.setPassingTouchdowns(8)
				.setRushingAttempts(9)
				.setRushingYards(10)
				.setRushingTouchdowns(11)
				.setRushingLong(12)
				.setRushingLongTouchdown(13)
				.setReceptions(14)
				.setReceivingYards(15)
				.setReceivingTouchdowns(16)
				.setReceivingLong(17)
				.setReceivingLongTouchdown(18)
				.setFumbles(19)
				.setFumblesLost(20)
				.setFumblesRecovered(21)
				.setFumbleYards(22)
				.verify();

		StatsAssertions.statsAssertions(results.getTeStats())
				.setPassingAttempts(5)
				.setPassingCompletions(6)
				.setPassingYards(7)
				.setPassingTouchdowns(8)
				.setRushingAttempts(9)
				.setRushingYards(10)
				.setRushingTouchdowns(11)
				.setRushingLong(12)
				.setRushingLongTouchdown(13)
				.setReceptions(14)
				.setReceivingYards(15)
				.setReceivingTouchdowns(16)
				.setReceivingLong(17)
				.setReceivingLongTouchdown(18)
				.setFumbles(19)
				.setFumblesLost(20)
				.setFumblesRecovered(21)
				.setFumbleYards(22)
				.verify();
	}

	@Test
	public void testPredictionStatsTwoPlayersPer() {
		PredictionResults.Builder builder = PredictionResults.builder();

		builder.increment(
				Position.QB,
				PlayerStats.builder("Colt McCoy", "1001")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.RB,
				PlayerStats.builder("Jamaal Charles", "1002")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.RB,
				PlayerStats.builder("D'Onta Foreman", "1001")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.WR,
				PlayerStats.builder("Roy Williams", "1003")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.WR,
				PlayerStats.builder("Jordan Shipley", "1001")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		builder.increment(
				Position.TE,
				PlayerStats.builder("JerMichael Finley", "1004")
						.passingAttempts(10)
						.passingCompletions(11)
						.passingYards(12)
						.passingTouchdowns(13)
						.rushingAttempts(14)
						.rushingYards(15)
						.rushingTouchdowns(16)
						.rushingLong(17)
						.rushingLongTouchdown(18)
						.receptions(19)
						.receivingYards(20)
						.receivingTouchdowns(21)
						.receivingLong(22)
						.receivingLongTouchdown(23)
						.fumbles(24)
						.fumblesLost(25)
						.fumblesRecovered(26)
						.fumbleYards(27)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.incrementPassingCompletions(17)
						.incrementPassingYards(19)
						.incrementPassingTouchdowns(21)
						.incrementRushingAttempts(23)
						.incrementRushingYards(25)
						.incrementRushingTouchdowns(27)
						.incrementRushingLong(29)
						.incrementRushingLongTouchdown(31)
						.incrementReceptions(33)
						.incrementReceivingYards(35)
						.incrementReceivingTouchdowns(37)
						.incrementReceivingLong(39)
						.incrementReceivingLongTouchdown(41)
						.incrementFumbles(43)
						.incrementFumblesLost(45)
						.incrementFumblesRecovered(47)
						.incrementFumbleYards(49)
						.build()
		);

		builder.increment(
				Position.TE,
				PlayerStats.builder("", "1001")
						.build(),
				AccuracyStats.builder()
						.build()
		);

		PredictionResults results = builder.build();

		StatsAssertions.statsAssertions(results.getQbStats())
				.setPassingAttempts(2.5)
				.setPassingCompletions(3.0)
				.setPassingYards(3.5)
				.setPassingTouchdowns(4.0)
				.setRushingAttempts(4.5)
				.setRushingYards(5.0)
				.setRushingTouchdowns(5.5)
				.setRushingLong(6.0)
				.setRushingLongTouchdown(6.5)
				.setReceptions(7.0)
				.setReceivingYards(7.5)
				.setReceivingTouchdowns(8.0)
				.setReceivingLong(8.5)
				.setReceivingLongTouchdown(9.0)
				.setFumbles(9.5)
				.setFumblesLost(10.0)
				.setFumblesRecovered(10.5)
				.setFumbleYards(11.0)
				.verify();

		StatsAssertions.statsAssertions(results.getRbStats())
				.setPassingAttempts(2.5)
				.setPassingCompletions(3.0)
				.setPassingYards(3.5)
				.setPassingTouchdowns(4.0)
				.setRushingAttempts(4.5)
				.setRushingYards(5.0)
				.setRushingTouchdowns(5.5)
				.setRushingLong(6.0)
				.setRushingLongTouchdown(6.5)
				.setReceptions(7.0)
				.setReceivingYards(7.5)
				.setReceivingTouchdowns(8.0)
				.setReceivingLong(8.5)
				.setReceivingLongTouchdown(9.0)
				.setFumbles(9.5)
				.setFumblesLost(10.0)
				.setFumblesRecovered(10.5)
				.setFumbleYards(11.0)
				.verify();

		StatsAssertions.statsAssertions(results.getWrStats())
				.setPassingAttempts(2.5)
				.setPassingCompletions(3.0)
				.setPassingYards(3.5)
				.setPassingTouchdowns(4.0)
				.setRushingAttempts(4.5)
				.setRushingYards(5.0)
				.setRushingTouchdowns(5.5)
				.setRushingLong(6.0)
				.setRushingLongTouchdown(6.5)
				.setReceptions(7.0)
				.setReceivingYards(7.5)
				.setReceivingTouchdowns(8.0)
				.setReceivingLong(8.5)
				.setReceivingLongTouchdown(9.0)
				.setFumbles(9.5)
				.setFumblesLost(10.0)
				.setFumblesRecovered(10.5)
				.setFumbleYards(11.0)
				.verify();

		StatsAssertions.statsAssertions(results.getTeStats())
				.setPassingAttempts(2.5)
				.setPassingCompletions(3.0)
				.setPassingYards(3.5)
				.setPassingTouchdowns(4.0)
				.setRushingAttempts(4.5)
				.setRushingYards(5.0)
				.setRushingTouchdowns(5.5)
				.setRushingLong(6.0)
				.setRushingLongTouchdown(6.5)
				.setReceptions(7.0)
				.setReceivingYards(7.5)
				.setReceivingTouchdowns(8.0)
				.setReceivingLong(8.5)
				.setReceivingLongTouchdown(9.0)
				.setFumbles(9.5)
				.setFumblesLost(10.0)
				.setFumblesRecovered(10.5)
				.setFumbleYards(11.0)
				.verify();
	}

	@Test
	public void testPredictionStatsTenPlayers() {
		PredictionResults.Builder builder = PredictionResults.builder();

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(10)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(11)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(15)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(17)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(1)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(4)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(1500)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(1504)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(0)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(5)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(9)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(15)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(9)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(16)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(49)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(57)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(9)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(0)
						.build()
		);

		builder.increment(
				Position.QB,
				PlayerStats.builder("Vince Young", "1001")
						.passingAttempts(10)
						.build(),
				AccuracyStats.builder()
						.incrementPlayersRecieved()
						.incrementPassingAttempts(0)
						.build()
		);

		PredictionResults results = builder.build();

		StatsAssertions.statsAssertions(results.getQbStats())
				.setPassingAttempts(5.5)
				.verify();
	}
}