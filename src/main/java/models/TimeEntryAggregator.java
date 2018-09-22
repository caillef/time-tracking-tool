package models;

import java.util.UUID;

public class TimeEntryAggregator extends TimeEntry {
    private long nbHours;

    public TimeEntryAggregator(UUID taskId) {
        super(taskId);
        nbHours = 0;
    }

    public long getNbHours() {
        return nbHours;
    }

    public void setNbHours(long nbHours) {
        this.nbHours = nbHours;
    }

    public void addNbHours(long nbHours) {
        this.nbHours += nbHours;
    }
}
