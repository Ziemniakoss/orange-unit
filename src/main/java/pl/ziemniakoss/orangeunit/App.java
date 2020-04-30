package pl.ziemniakoss.orangeunit;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class App {
	public static void main(String[] args) {
		ArrayList<TimeInterval> arrayList = new ArrayList<>();
		arrayList.add(null);
		arrayList.add(new TimeInterval(LocalTime.parse("00:11"), LocalTime.parse("00:15")));
		arrayList.add(new TimeInterval(LocalTime.parse("00:21"), LocalTime.parse("00:35")));
		arrayList.add(new TimeInterval(LocalTime.parse("00:11"), LocalTime.parse("00:12")));
		arrayList.add(new TimeInterval(LocalTime.parse("00:45"), LocalTime.parse("00:55")));
		List<TimeInterval> sorted = arrayList.stream().filter(Objects::nonNull).sorted().collect(Collectors.toList());
		System.out.println(sorted);

	}

	private static void aa(String date) {
		System.out.println(LocalTime.parse(date));
	}
}

