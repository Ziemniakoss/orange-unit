package pl.ziemniakoss.orangeunit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalTest {

	@Test
	public void findCommonNullInput() {
		TimeInterval interval = new TimeInterval(LocalTime.parse("09:30"), LocalTime.parse("16:30"));
		assertNull(interval.findCommon(null));
	}

	@ParameterizedTest
	@MethodSource
	public void findCommonWithCommon(TimeInterval i1, TimeInterval i2, TimeInterval expected) {
		assertEquals(expected, i1.findCommon(i2));
	}

	public static Stream<Arguments> findCommonWithCommon() {
		return Stream.of(
				Arguments.of(
						new TimeInterval("00:00", "16:00"),
						new TimeInterval("00:00", "16:00"),
						new TimeInterval("00:00", "16:00")),
				Arguments.of(
						new TimeInterval("16:00", "18:00"),
						new TimeInterval("16:00", "17:00"),
						new TimeInterval("16:00", "17:00")),
				Arguments.of(
						new TimeInterval("16:00", "17:00"),
						new TimeInterval("16:00", "18:00"),
						new TimeInterval("16:00", "17:00")),
				Arguments.of(
						new TimeInterval("16:00", "19:30"),
						new TimeInterval("12:51", "19:30"),
						new TimeInterval("16:00", "19:30")),
				Arguments.of(
						new TimeInterval("12:51", "19:30"),
						new TimeInterval("16:00", "19:30"),
						new TimeInterval("16:00", "19:30")),
				Arguments.of(
						new TimeInterval("10:39", "22:10"),
						new TimeInterval("13:51", "16:43"),
						new TimeInterval("13:51", "16:43")
				), Arguments.of(
						new TimeInterval("13:51", "16:43"),
						new TimeInterval("10:39", "22:10"),
						new TimeInterval("13:51", "16:43")
				));
	}

	@ParameterizedTest
	@MethodSource
	public void findCommonWithoutCommon(TimeInterval i1, TimeInterval i2) {
		assertNull(i1.findCommon(i2));
	}

	public static Stream<Arguments> findCommonWithoutCommon() {
		return Stream.of(
				Arguments.of(
						new TimeInterval("19:00", "20:00"),
						new TimeInterval("15:00", "18:00"))

		);
	}

	@Test
	public void findDifferenceNullInput() {
		TimeInterval interval = new TimeInterval("00:00", "14:30");
		TimeInterval[] result = interval.findDifference(null);
		assertEquals(1, result.length);
		assertEquals(interval, result[0]);
	}

	@Test
	public void findDifferenceSameInputs() {
		TimeInterval interval = new TimeInterval("04:50", "05:12");
		TimeInterval[] result = interval.findDifference(interval);
		assertEquals(0, result.length);
	}

	@ParameterizedTest
	@MethodSource
	public void findDifference(TimeInterval i1, TimeInterval i2, TimeInterval[] expected) {
		TimeInterval[] result = i1.findDifference(i2);
		assertArrayEquals(expected,result);
	}

	public static Stream<Arguments> findDifference() {
		return Stream.of(
				Arguments.of(
						new TimeInterval("10:00", "12:00"),
						new TimeInterval("10:00", "12:00"),
						new TimeInterval[0]
				),
				Arguments.of(
						new TimeInterval("10:00", "15:00"),
						new TimeInterval("10:00", "12:00"),
						new TimeInterval[]{new TimeInterval("12:00", "15:00")}
				),
				Arguments.of(
						new TimeInterval("13:00", "16:00"),
						new TimeInterval("14:40", "16:00"),
						new TimeInterval[]{new TimeInterval("13:00", "14:40")}
				),
				Arguments.of(
						new TimeInterval("09:00", "11:56"),
						new TimeInterval("11:56", "12:00"),
						new TimeInterval[]{new TimeInterval("09:00", "11:56")}
				),
				Arguments.of(
						new TimeInterval("05:34", "07:14"),
						new TimeInterval("16:45", "17:56"),
						new TimeInterval[]{new TimeInterval("05:34", "07:14")}
				),
				Arguments.of(
						new TimeInterval("10:00", "20:00"),
						new TimeInterval("12:00", "18:00"),
						new TimeInterval[]{new TimeInterval("10:00", "12:00"), new TimeInterval("18:00", "20:00")}
				),
				Arguments.of(
						new TimeInterval("12:00", "18:00"),
						new TimeInterval("10:00", "20:00"),
						new TimeInterval[0]
				)
		);
	}


}