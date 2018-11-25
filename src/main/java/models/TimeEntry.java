package models;

import controllers.TasksManager;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class TimeEntry implements IElement {
    protected TasksManager tasksManager;
    protected UUID id;
    protected UUID taskId;
    private Date start;
    private Date end;

    public TimeEntry(TasksManager tasksManager, UUID taskId, Date start, Date end, UUID id) {
        this.tasksManager = tasksManager;
        this.id = id;
        this.taskId = taskId;
        this.start = start;
        this.end = end;
    }

    public TimeEntry(TasksManager tasksManager, UUID taskId, Date start, Date end) {
        this.tasksManager = tasksManager;
        this.taskId = taskId;
        this.start = start;
        this.end = end;
        this.id = UUID.randomUUID();
    }

    public TimeEntry(TasksManager tasksManager, UUID taskId) {
        this.tasksManager = tasksManager;
        this.id = UUID.randomUUID();
        this.taskId = taskId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String toString() {
        Optional<Task> optTask = tasksManager.getList().stream().filter(t -> t.getId().equals(this.taskId)).findFirst();
        Task task = optTask.orElse(null);
        if (task != null) {
            return task.getTitle() + " " + start + " " + ((end.getTime() - start.getTime()) / 3600000) + "h";
        }
        return "Task deleted";
    }
}
