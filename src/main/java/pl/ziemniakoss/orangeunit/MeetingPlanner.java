package pl.ziemniakoss.orangeunit;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		//stwórz posorowaną listę wszystkich spotkań
		List<TimeInterval> allMeetings = Stream.concat(calendar1.getPlannedMeetings().stream(), calendar2.getPlannedMeetings().stream())
				.filter(Objects::nonNull)
				.sorted()
				.collect(Collectors.toList());

		Stack<TimeInterval> possibleMeetings = new Stack<>();
		possibleMeetings.push(common);
		for (int i = 0; i < allMeetings.size() && !possibleMeetings.empty(); i++) {
			TimeInterval interval = possibleMeetings.pop();
			TimeInterval[] difference = interval.findDifference(allMeetings.get(i));
			for (TimeInterval x : difference) {
				possibleMeetings.push(x);
			}
		}
		return possibleMeetings.stream()
				.filter(interval -> !interval.getStart().plus(duration).isAfter(interval.getEnd()))
				.collect(Collectors.toList());
	}
}
