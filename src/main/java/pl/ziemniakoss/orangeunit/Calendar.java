package pl.ziemniakoss.orangeunit;

import java.util.ArrayList;
import java.util.Collection;

public class Calendar {
    private TimeInterval workingHours;
    private Collection<TimeInterval> plannedMeetings;

    public Calendar(TimeInterval workingHours, Collection<TimeInterval> plannedMeetings) {
        if(workingHours == null){
            throw new IllegalArgumentException("Working hours can't be null");
        }
        this.workingHours = workingHours;
        this.plannedMeetings = plannedMeetings;
        if(plannedMeetings == null){
            this.plannedMeetings = new ArrayList<>();
        }
        for(TimeInterval meeting : this.plannedMeetings){
            if(meeting.getStart().isBefore(workingHours.getStart()) || meeting.getEnd().isAfter(workingHours.getEnd())){
                throw new IllegalArgumentException("Meeting "+meeting+" does not fit in working hours("+ workingHours+")");
            }
        }
    }

    public TimeInterval getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(TimeInterval workingHours) {
        this.workingHours = workingHours;
    }

    public Collection<TimeInterval> getPlannedMeetings() {
        return plannedMeetings;
    }

    public void setPlannedMeetings(Collection<TimeInterval> plannedMeetings) {
        this.plannedMeetings = plannedMeetings;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\n\t\"workingHours\": " + workingHours +
                ",\n\t\"plannedMeetings\": " + plannedMeetings +
                "\n}";
    }
}
