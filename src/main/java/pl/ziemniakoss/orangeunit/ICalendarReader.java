package pl.ziemniakoss.orangeunit;

import java.io.IOException;
import java.nio.file.Path;

public interface ICalendarReader {
	Calendar read(Path inFile) throws IOException;
}
