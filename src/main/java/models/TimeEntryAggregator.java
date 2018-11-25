package models;

import controllers.TasksManager;

import java.util.Optional;
import java.util.UUID;

public class TimeEntryAggregator extends TimeEntry {
    private long nbHours;

    public TimeEntryAggregator(TasksManager tasksManager, UUID taskId) {
        super(tasksManager, taskId);
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

    public String toString() {
        Optional<Task> optTask = tasksManager.getList().stream().filter(t -> t.getId().equals(this.taskId)).findFirst();
        Task task = optTask.orElse(null);
        if (task != null) {
            return task.getTitle() + " " + getNbHours() + "h";
        }
        return "Task deleted";
    }

}
