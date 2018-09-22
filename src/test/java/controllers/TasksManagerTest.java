package controllers;

import models.Task;
import models.TaskType;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TasksManagerTest {

    private TasksManager manager;
    private Task task;

    @Before
    public void setUp() throws Exception {
        manager = new TasksManager();
        task = new Task("Homework", "Work for school");
    }

    @Test
    public void addItem() {
        assertEquals(0, manager.getList().size());
        manager.addItem(task);
        assertEquals(1, manager.getList().size());
        assertEquals(task.getId(), manager.getList().get(0).getId());
    }

    @Test
    public void deleteItem() {
        assertEquals(0, manager.getList().size());
        manager.addItem(task);
        UUID id = task.getId();
        assertEquals(1, manager.getList().size());

        task = new Task("Freelance", "Work for school");
        manager.addItem(task);

        assertEquals(2, manager.getList().size());
        manager.deleteItem(task.getId());

        assertEquals(1, manager.getList().size());
        assertEquals(id, manager.getList().get(0).getId());
        manager.deleteItem(id);
        assertEquals(0, manager.getList().size());
    }


    @Test
    public void renameTask() {
        manager.addItem(task);
        assertEquals(1, manager.getList().size());
        assertEquals(task.getId(), manager.getList().get(0).getId());
        manager.renameTask(task.getId(), "Playing");
        assertEquals("Playing", manager.getList().get(0).getTitle());
        manager.deleteItem(task.getId());
        assertEquals(0, manager.getList().size());
    }
}