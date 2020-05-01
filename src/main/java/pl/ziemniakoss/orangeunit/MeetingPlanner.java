package pl.ziemniakoss.orangeunit;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class MeetingPlanner {
	/**
	 * Znajduje możliwe przedziały czasowe o długości takiej samej lub dłuższej niż {@code duration}.
	 *
	 * @param calendar1 kalendarz pierwszej osoby
	 * @param calendar2 kalendarz drugiej osoby
	 * @param duration  długość spotkania
	 * @return zbiór wszystkich możliwych spotkań
	 */
	public Collection<TimeInterval> findPossibleMeetings(Calendar calendar1, Calendar calendar2, Duration duration) {
		if (calendar1 == null || calendar2 == null) {
			return new ArrayList<>();
		}
		TimeInterval common = calendar1.getWorkingHours().findCommon(calendar2.getWorkingHours());
		if (common == null) {
			return new ArrayList<>();
		}

		PriorityQueue<TimeInterval> allMeetings = new PriorityQueue<>();
		allMeetings.addAll(calendar1.getPlannedMeetings());
		allMeetings.addAll(calendar2.getPlannedMeetings());

		Stack<TimeInterval> possibleMeetings = new Stack<>();
		possibleMeetings.push(common);
		while (!possibleMeetings.isEmpty() && !allMeetings.isEmpty()) {
			TimeInterval interval = possibleMeetings.pop();
			TimeInterval[] difference = interval.findDifference(allMeetings.poll());
			for (TimeInterval x : difference) {
				possibleMeetings.push(x);
			}
		}
		return possibleMeetings.stream()
				.filter(interval -> !interval.getStart().plus(duration).isAfter(interval.getEnd()))
				.collect(Collectors.toList());
	}
}
