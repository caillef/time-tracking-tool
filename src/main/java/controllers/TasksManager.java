package controllers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import models.Task;

public class TasksManager extends AListManager<Task> {
    private DatabaseManager db;

    public TasksManager(DatabaseManager db) {
        super(db);
        this.db = db;
    }

    public TasksManager() {
        super();
        this.db = null;
    }

    public void renameTask(UUID id, String title) {
        Optional<Task> optTask = list.stream()
                .filter(t -> t.getId() == id).findFirst();
        Task task = optTask.orElse(null);
        if (task != null) {
            task.setTitle(title);
            if (db != null)
                db.UpdateItem(task, title, null);
        }
    }

    @Override
    public Task getById(UUID id) {
        if (this.db == null)
            return (super.getById(id));
        else
            return db.GetTask(id).orElse(null);
    }

    @Override
    public ArrayList<Task> getList() {
        if (this.db == null)
            return (super.getList());
        else
            return db.GetTasks();
    }

    @Override
    public void clear() {
        if (this.db == null)
            super.clear();
        else
            db.ClearTasks();
    }
}
