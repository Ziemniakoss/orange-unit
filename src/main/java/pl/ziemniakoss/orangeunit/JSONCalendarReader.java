package pl.ziemniakoss.orangeunit;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Wczytuje dane o kalendarzu z pliku z JSON.
 * Przykładowy akceptowalny JSON:
 * <pre>
 * {
 *   "working_hours":
 *   {
 *     "start": "09:00",
 *     "end": "20:00"
 *   },
 *   "planned_meeting":
 *   [
 *     {
 *    	  "start": "09:00",
 *    	  "end": "10:30"
 *     },
 *     {
 *        "start": "12:00",
 *        "end": "13:00"
 *     }
 *   ]
 * }
 * </pre>
 * Pole working_hours jest wymagane. Kiedy planned_meeting będzie null lub puste kalendarz
 * nie będzie zawierał spotkań.
 */
public class JSONCalendarReader implements ICalendarReader {

	@Override
	public Calendar read(Path inFile) throws IOException {
		if (inFile == null) {
			throw new IllegalArgumentException("inFile must not be null");
		}
		String fileContent = Files.readAllLines(inFile).stream().collect(Collectors.joining(System.lineSeparator()));
		JSONObject json = new JSONObject(fileContent);
		if (!json.has("working_hours")) {
			throw new IllegalArgumentException("json's field working_hours is required");
		}
		TimeInterval workingHours = extractTimeInterval(json.getJSONObject("working_hours"));
		Collection<TimeInterval> meetings = extractMeetings(json);
		return new Calendar(workingHours, meetings);
	}

	/**
	 * Zamienia obiekt json na TimeInterval. Obiekt JSON musi mieć dwa pola:
	 * <ul>
	 *     <li>start: typu string w formacie "hh:mm"</li>
	 *     <li>end: typu string w formacie "hh:mm"</li>
	 * </ul>
	 * Przykładowy akceptowalny json:
	 * <pre>
	 * {
	 *   "start": "12:00",
	 *   "end": "13:00"
	 * }
	 * </pre>
	 *
	 * @param json objekt json z danymi o przedziale czasowym
	 * @return przedział czasowy
	 * @throws IllegalArgumentException kiedy
	 *                                  <ul>
	 *                                      <li>czas end był przed start</li>
	 *                                      <li>json nie zawierał wymaganych pól</li>
	 *                                  </ul>
	 */
	private TimeInterval extractTimeInterval(JSONObject json) {
		if (json == null) {
			return null;
		}
		if (!json.has("start") || !json.has("end")) {
			throw new IllegalArgumentException("Fields start and end are required");
		}
		LocalTime start = LocalTime.parse(json.getString("start"));
		LocalTime end = LocalTime.parse(json.getString("end"));
		return new TimeInterval(start, end);
	}

	/**
	 * Wydobywa z json dane o spotkaniach. JSON musi być tablicą obiektów
	 * json opisujących przedziały czasowe(patrz {@link #extractTimeInterval(JSONObject)}).
	 *
	 * @param json json zawierający dane o spotkaniach lub null
	 * @return listę przedziałów czasowych z jsona lub null gdy {@code json} jest null
	 * @throws IllegalArgumentException gdy planned_meeting nie jest tablicą obiektów json lub
	 *                                  jeden z elementów planned_meeting zawierał błędny przedział czasowy(patrz {@link #extractTimeInterval(JSONObject)}).
	 */
	private Collection<TimeInterval> extractMeetings(JSONObject json) {
		if (!json.has("planned_meeting")) {
			return null;
		}
		JSONArray jsonMeetings;
		try {
			jsonMeetings = json.getJSONArray("planned_meeting");
		} catch (JSONException e) {
			throw new IllegalArgumentException("Field planned_meeting must be array");
		}
		Collection<TimeInterval> meetings = new ArrayList<>(jsonMeetings.length());
		for (int i = 0; i < jsonMeetings.length(); i++) {
			meetings.add(extractTimeInterval(jsonMeetings.getJSONObject(i)));
		}
		return meetings;
	}
}
