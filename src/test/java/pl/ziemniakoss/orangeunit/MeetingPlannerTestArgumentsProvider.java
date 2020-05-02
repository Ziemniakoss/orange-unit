package pl.ziemniakoss.orangeunit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeetingPlannerTestArgumentsProvider implements ArgumentsProvider {
	private static final int TEST_COUNT = 4;
	private static final String CASES_DIRECTORY = "src" +File.separator +"test"+File.separator+"resources"+File.separator +"MeetingPlannerTest";
	private final JSONCalendarReader reader = new JSONCalendarReader();

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		Stream.Builder<Arguments> streamBuilder = Stream.builder();
		for (int i = 1; i <= TEST_COUNT; i++) {
			streamBuilder.add(readCase(i));
		}
		return streamBuilder.build();
	}

	private Arguments readCase(int number) throws IOException {
		String testCase =
				Files.readAllLines(Paths.get(CASES_DIRECTORY,number+".json")).stream()
				.collect(Collectors.joining(System.lineSeparator()));
		JSONObject testCaseJSON = new JSONObject(testCase);
		return Arguments.of(
				reader.read(testCaseJSON.getJSONObject("cal1")),
				reader.read(testCaseJSON.getJSONObject("cal2")),
				extractDuration(testCaseJSON.getString("duration")),
				extractTimeIntervals(testCaseJSON.getJSONArray("expected"))
		);
	}

	private List<TimeInterval> extractTimeIntervals(JSONArray expectedJSON) {
		List<TimeInterval> result = new ArrayList<>();
		for (int i = 0; i < expectedJSON.length(); i++) {
			JSONObject intervalJSON = expectedJSON.getJSONObject(i);
			result.add(new TimeInterval(intervalJSON.getString("start"), intervalJSON.getString("end")));
		}
		return result;
	}

	private Duration extractDuration(String duration) {
		if (duration == null || duration.length() != 5) {
			throw new IllegalArgumentException("Duration of test case must have format \"hh:mm\"");
		}
		LocalTime time = LocalTime.parse(duration);
		return Duration.ofMinutes(time.getHour() * 60 + time.getMinute());
	}
}
