package pl.ziemniakoss.orangeunit;

import java.time.LocalTime;
import java.util.Objects;

public class TimeInterval implements Comparable<TimeInterval> {
	private LocalTime start;
	private LocalTime end;

	public TimeInterval(String start, String end) {
		this(LocalTime.parse(start), LocalTime.parse(end));
	}

	public TimeInterval(LocalTime start, LocalTime end) {
		if (start == null || end == null) {
			throw new IllegalArgumentException("values can't be null");
		}
		if (start.isAfter(end)) {
			throw new IllegalArgumentException(String.format("Start(%s) is after end(%s)", start, end));
		}
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return String.format("[\"%s\", \"%s\"]", start, end);
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

	/**
	 * Porównuje dwa przedziały czasowe. Przedział jest mniejszy od drugiego, gdy
	 * gdy zaczyna się wcześniej lub rozpoczyna się w tym samym momencie, ale trwa dłużej.
	 *
	 * @param o przedział do porównania
	 * @return -1 jeżeli podany przedział zaczyna się później lub gdy zaczyna się w tym samym momencie
	 * ale kończy się wcześniej. 1 jeżeli przedział zaczyna się wcześniej lub gdy zaczyna się w tym samym momencie ale
	 * kończy się później. 0 gdy przedziały są takie same.
	 */
	@Override
	public int compareTo(TimeInterval o) {
		if (o == null) {
			return -1;
		}
		if (start.isBefore(o.start)) {
			return -1;
		} else if (start.equals(o.start)) {
			return -end.compareTo(o.end);
		}
		return 1;
	}

	/**
	 * Znajduje część wspólną dwóch przedziałów czasowych
	 *
	 * @return interwał wspólny dla podanych interwałów albo null
	 */
	public TimeInterval findCommon(TimeInterval interval) {
		if (interval == null) {
			return null;
		}
		if (this.getStart().isAfter(interval.getStart())) {
			return findCommon(interval, this);
		}
		return findCommon(this, interval);
	}

	/**
	 * Znajduje wspólną część przedziałów czasowych. Metoda zakłada, że
	 * <b>{@code interval1} jest {@code interval2}</b>
	 *
	 * @return część wspólną lub null gdy takiej nie mają
	 */
	private TimeInterval findCommon(TimeInterval interval1, TimeInterval interval2) {
		if (interval1.getStart().equals(interval2.getStart())) {
			LocalTime end = interval1.getEnd().isBefore(interval2.getEnd()) ? interval1.getEnd() : interval2.getEnd();
			return new TimeInterval(interval1.getStart(), end);
		}
		if (interval1.getEnd().isBefore(interval2.getStart())) {
			return null;
		}
		LocalTime end = interval1.getEnd().isBefore(interval2.getEnd()) ? interval1.getEnd() : interval2.getEnd();
		return new TimeInterval(interval2.getStart(), end);
	}

	/**
	 * Znajduje różnicę przedziału obecnego i {@code interval}
	 *
	 * @param interval przedział do odjęcia
	 * @return wynik odjemowania od obecnego przedziału {@code interval}
	 */
	public TimeInterval[] findDifference(TimeInterval interval) {
		if (interval == null) {
			return new TimeInterval[]{this};
		}
		TimeInterval common = findCommon(interval);
		if (common == null) {
			return new TimeInterval[]{this};
		} else if (common.equals(this)) {
			return new TimeInterval[0];
		}
		if (common.start.equals(start)) {
			return new TimeInterval[]{new TimeInterval(common.end, end)};
		} else if (common.end.equals(end)) {
			return new TimeInterval[]{new TimeInterval(start, common.start)};
		}
		return new TimeInterval[]{
				new TimeInterval(start, common.start),
				new TimeInterval(common.end, end)
		};
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TimeInterval interval = (TimeInterval) o;
		return start.equals(interval.start) && end.equals(interval.end);
	}

	@Override
	public int hashCode() {
		return Objects.hash(start, end);
	}
}
