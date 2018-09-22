package controllers;

import java.util.Optional;
import java.util.UUID;

import models.Task;

public class TasksManager extends AListManager<Task> {
    public TasksManager() {
        super();
    }

    public void renameTask(UUID id, String title) {
        Optional<Task> optTask = list.stream()
                .filter(t -> t.getId() == id).findFirst();
        Task task = optTask.orElse(null);
        if (task != null) {
            task.setTitle(title);
        }
    }
}
