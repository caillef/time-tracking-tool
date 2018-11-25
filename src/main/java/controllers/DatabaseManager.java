package controllers;

import models.IElement;
import models.Task;
import models.TimeEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class DatabaseManager {
    private Connection db;
    private TasksManager tasksManager;

    public DatabaseManager() {
        try {
            this.db = DriverManager.getConnection("jdbc:postgresql://bvrcidpwfhs3kwo-postgresql.services.clever-cloud.com:5432/bvrcidpwfhs3kwo", "uw7d4una0xuas26uz3lm", "SO1rsMlBGNlGdpzOmpPg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTasksManager(TasksManager tasksManager) {
        this.tasksManager = tasksManager;
    }

    public <T> void AddItem(T elem) {
        try {
            Statement stmt = db.createStatement();
            String strSelect = "";
            switch (elem.getClass().getSimpleName()) {
                case "Task":
                    Task task = (Task) elem;
                    strSelect = String.format("INSERT INTO tasks (title, details, id) VALUES ('%s','%s','%s')", task.getTitle(), task.getDetails(), task.getId());
                    break;
                case "TimeEntry":
                    TimeEntry entry = (TimeEntry) elem;
                    strSelect = String.format("INSERT INTO timeentries (task_id, start_date, end_date, id) VALUES ('%s', '%s', '%s', '%s')", entry.getTaskId(), entry.getStart().getTime(), entry.getEnd().getTime(), entry.getId());
                    break;
                default:
                    break;
            }
            stmt.executeUpdate(strSelect);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void ReplaceItem(UUID id, T newElem) {
        try {
            Statement stmt = db.createStatement();
            String strSelect = "";
            switch (newElem.getClass().getSimpleName()) {
                case "Task":
                    Task newTask = (Task) newElem;
                    strSelect = String.format("UPDATE tasks SET title='%s', details='%s' WHERE id='%s'", newTask.getTitle(), newTask.getDetails(), id);
                    break;
                case "TimeEntry":
                    TimeEntry newEntry = (TimeEntry) newElem;
                    strSelect = String.format("UPDATE timeentries SET task_id='%s', start_date='%s', end_date='%s' WHERE id='%s'", newEntry.getTaskId(), newEntry.getStart().getTime(), newEntry.getEnd().getTime(), newEntry.getId());
                    break;
                default:
                    break;
            }
            stmt.executeUpdate(strSelect);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void UpdateItem(T item, String title, UUID taskId) {
        try {
            Statement stmt = db.createStatement();
            String strSelect = "";
            switch (item.getClass().getSimpleName()) {
                case "Task":
                    Task task = (Task) item;
                    strSelect = String.format("UPDATE tasks SET title='%s' WHERE id='%s'", title, task.getId());
                    break;
                case "TimeEntry":
                    TimeEntry entry = (TimeEntry) item;
                    strSelect = String.format("UPDATE timeentries SET task_id='%s' WHERE id='%s'", taskId, entry.getId());
                    break;
                default:
                    break;
            }
            stmt.executeUpdate(strSelect);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends IElement> void DeleteItem(T item) {
        try {
            Statement stmt = db.createStatement();
            String strSelect = String.format("DELETE FROM %s WHERE id='%s'", (item.getClass().getSimpleName().equals("Task") ? "tasks" : "timeentries"), item.getId());
            stmt.executeUpdate(strSelect);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Task> GetTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            String strSelect = "SELECT id, title, details FROM tasks";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                tasks.add(new Task(rset.getString("title"), rset.getString("details"), UUID.fromString(rset.getString("id"))));
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public Optional<Task> GetTask(UUID uuid) {
        Optional<Task> task = Optional.empty();
        try {
            Statement stmt = db.createStatement();
            String strSelect = String.format("SELECT id, title, details FROM tasks WHERE id='%s'", uuid);
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                task = Optional.of(new Task(rset.getString("title"), rset.getString("details"), UUID.fromString(rset.getString("id"))));
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    public ArrayList<TimeEntry> GetEntries() {
        ArrayList<TimeEntry> entries = new ArrayList<>();
        try {
            Statement stmt = db.createStatement();
            String strSelect = "SELECT id, task_id, start_date, end_date FROM timeentries";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                entries.add(new TimeEntry(this.tasksManager, UUID.fromString(rset.getString("task_id")), new Date(rset.getLong("start_date")), new Date(rset.getLong("end_date")), UUID.fromString(rset.getString("id"))));
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    public Optional<TimeEntry> GetEntry(UUID uuid) {
        Optional<TimeEntry> entry = Optional.empty();
        try {
            Statement stmt = db.createStatement();
            String strSelect = String.format("SELECT id, task_id, start_date, end_date FROM timeentries WHERE id='%s'", uuid);
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                entry = Optional.of(new TimeEntry(this.tasksManager, UUID.fromString(rset.getString("task_id")), new Date(rset.getLong("start_date")), new Date(rset.getLong("end_date")), UUID.fromString(rset.getString("id"))));
            }
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public void ClearTasks() {
        try {
            Statement stmt = db.createStatement();
            String strSelect = String.format("DELETE FROM tasks");
            stmt.executeUpdate(strSelect);
            AddItem(new Task("Homework", "Laurea University"));
            AddItem(new Task("Work", "AILiveSim"));
            AddItem(new Task("Playing music", "Guitar or drums"));
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void ClearEntries() {
        try {
            Statement stmt = db.createStatement();
            String strSelect = String.format("DELETE FROM timeentries");
            stmt.executeUpdate(strSelect);
        } catch(SQLException se) {
            se.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
