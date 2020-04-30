package pl.ziemniakoss.orangeunit;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

import java.util.stream.Collectors;

/**
 * Wczytuje dane o kalendarzu z pliku z JSON.
 * Przykładowy akceptowalny JSON:
 * <pre>
 * {
 *   working_hours:
 *   {
 *     start: "09:00",
 *     end: "20:00"
 *   },
 *   planned_meeting:
 *   [
 *     {
 *    	  start: "09:00",
 *    	  end: "10:30"
 *     },
 *     {
 *        start: "12:00",
 *        end: "13:00"
 *     }
 *   ]
 * }
 * </pre>
 */
public class JSONCalendarReader implements ICalendarReader {

	@Override
	public Calendar read(Path inFile) throws IOException {
		String fileContent = Files.readAllLines(inFile).stream().collect(Collectors.joining(System.lineSeparator()));
		JSONObject json = new JSONObject(fileContent);
		//todo
		return null;
	}
}
