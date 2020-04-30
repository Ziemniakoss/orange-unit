package pl.ziemniakoss.orangeunit;

import java.time.LocalTime;

public class TimeInterval implements Comparable<TimeInterval> {
    private LocalTime start;
    private LocalTime end;

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

    @Override
    public int compareTo(TimeInterval o) {
        if (o == null) {
            return -1;
        }
        if (start.isBefore(o.start)) {
            return -1;
        } else if (end.isAfter(end)) {
            return 1;
        }
        return 0;
    }
}
