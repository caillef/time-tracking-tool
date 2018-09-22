package models;

import java.util.Date;
import java.util.UUID;

public class TimeEntry implements IElement {
    private UUID id;
    private UUID userId;
    private UUID taskId;
    private Date start;
    private Date end;

    public TimeEntry() {

    }

    public TimeEntry(UUID taskId, Date start, Date end, UUID userId, UUID id) {
        this.id = id;
        this.taskId = taskId;
        this.start = start;
        this.end = end;
        this.userId = userId;
    }

    public TimeEntry(UUID taskId, Date start, Date end, UUID userId) {
        this.id = UUID.randomUUID();
        this.taskId = taskId;
        this.start = start;
        this.end = end;
        this.userId = userId;
    }

    public TimeEntry(UUID taskId) {
        this.id = UUID.randomUUID();
        this.taskId = taskId;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
