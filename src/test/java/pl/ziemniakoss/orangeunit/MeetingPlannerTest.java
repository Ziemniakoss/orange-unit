package pl.ziemniakoss.orangeunit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MeetingPlannerTest {
	private final MeetingPlanner meetingPlanner = new MeetingPlanner();

	@ParameterizedTest
	@ArgumentsSource(MeetingPlannerTestArgumentsProvider.class)
	public void findPossibleMeetings(Calendar cal1, Calendar cal2, Duration duration, List<TimeInterval> expected) {
		Collection<TimeInterval> result = meetingPlanner.findPossibleMeetings(cal1, cal2, duration);
		assertEquals(expected, result);
	}



}