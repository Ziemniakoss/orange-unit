package pl.ziemniakoss.orangeunit;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAmount;
import java.util.Collection;

public class App {
	private final ICalendarReader calendarReader;

	public static void main(String[] args){
		if (args.length != 3) {
			System.err.println("Poprawne wywołanie:\n" +
					"\t [cal1] [cal2] [czasSpotkania]");
			System.exit(1);
		}
		LocalTime l = LocalTime.parse("00:30");
		System.out.println(l.plus(Duration.ofMinutes(30)));
		System.out.println(l);
		try {
			LocalTime duration = LocalTime.parse(args[2]);
			new App(new JSONCalendarReader()).start(args[0], args[1], duration);
		} catch (DateTimeParseException e) {
			System.err.println("Długość spotkania musi być w formacie \"hh:mm\"");
			System.exit(2);
		} catch (InvalidPathException e) {
			System.err.println("Niepoprawna ścieżka do pliku");
			System.exit(3);
		} catch (IOException e) {
			System.err.println("Błąd dostępu do pliku. Upewnij się, że plik istnieje");
			System.exit(4);
		}
	}

	public App(ICalendarReader calendarReader) {
		this.calendarReader = calendarReader;
	}

	public void start(String pathToCalendar1, String pathToCalendar2, LocalTime duration) throws IOException {
		Calendar calendar1 = calendarReader.read(Paths.get(pathToCalendar1));
		Calendar calendar2 = calendarReader.read(Paths.get(pathToCalendar2));
		MeetingPlanner meetingPlanner = new MeetingPlanner();
		Collection<TimeInterval> possibleMeetings = meetingPlanner.findPossibleMeetings(calendar1, calendar2, Duration.ofMinutes(duration.getHour() * 60 + duration.getMinute()));
		System.out.println(possibleMeetings);
	}
}

