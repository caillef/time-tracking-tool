package controllers;

import models.Task;
import models.TaskType;
import models.TimeEntry;
import models.TimeEntryAggregator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class EntriesManagerTest {

    private EntriesManager manager;
    private Task task;
    private TimeEntry timeEntry;

    @Before
    public void setUp() throws Exception {
        manager = new EntriesManager();
        task = new Task("Homework", "Work for school");
        timeEntry = new TimeEntry(task.getId(), new Date(), new Date(), UUID.randomUUID());
    }

    @Test
    public void addItem() {
        assertEquals(0, manager.getList().size());
        manager.addItem(timeEntry);
        assertEquals(1, manager.getList().size());
        assertEquals(task.getId(), manager.getList().get(0).getTaskId());
        assertEquals(timeEntry.getId(), manager.getList().get(0).getId());
    }

    @Test
    public void deleteItem() {
        assertEquals(0, manager.getList().size());
        manager.addItem(timeEntry);
        UUID id = timeEntry.getId();
        assertEquals(1, manager.getList().size());

        timeEntry = new TimeEntry(task.getId(), new Date(), new Date(), UUID.randomUUID());
        manager.addItem(timeEntry);

        assertEquals(2, manager.getList().size());
        manager.deleteItem(timeEntry.getId());

        assertEquals(1, manager.getList().size());
        assertEquals(id, manager.getList().get(0).getId());
        manager.deleteItem(id);
        assertEquals(0, manager.getList().size());
    }

    @Test
    public void changeTask() {
        manager.addItem(timeEntry);
        assertEquals(1, manager.getList().size());
        assertEquals(task.getId(), manager.getList().get(0).getTaskId());
        assertEquals(timeEntry.getId(), manager.getList().get(0).getId());
        UUID newUuid =  UUID.randomUUID();
        manager.changeTask(timeEntry.getId(), newUuid);
    }

    @Test
    public void getGlobalStatistics() {
        Task[] tasks = {
            new Task("Homework", "Work for school"),
            new Task("Freelance", "Working remotely"),
            new Task("Play", "Have some fun !")
        };
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
        for (int i = 0; i < 10; ++i) {
            manager.addItem(new TimeEntry(tasks[0].getId(), date, cal.getTime(), UUID.randomUUID()));
        }
        for (int i = 0; i < 5; ++i) {
            manager.addItem(new TimeEntry(tasks[1].getId(), date, cal.getTime(), UUID.randomUUID()));
        }
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour to have two hours time entries
        for (int i = 0; i < 7; ++i) {
            manager.addItem(new TimeEntry(tasks[2].getId(), date, cal.getTime(), UUID.randomUUID()));
        }
        assertEquals(10, manager.getGlobalStatistics().get(0).getNbHours()/3600000);
        assertEquals(5, manager.getGlobalStatistics().get(1).getNbHours()/3600000);
        assertEquals(14, manager.getGlobalStatistics().get(2).getNbHours()/3600000);
    }

    @Test
    public void getGlobalStatisticsWithRangeHours() {
        Task[] tasks = {
                new Task("Homework", "Work for school"),
                new Task("Freelance", "Working remotely"),
                new Task("Play", "Have some fun !")
        };
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
        for (int i = 0; i < 3; ++i) {
            manager.addItem(new TimeEntry(tasks[0].getId(), date, cal.getTime(), UUID.randomUUID()));
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        date = new Date();
        cal1.setTime(date);
        cal1.add(Calendar.HOUR_OF_DAY, 1);
        cal2.setTime(date);
        cal2.add(Calendar.HOUR_OF_DAY, 4);

        for (int i = 0; i < 2; ++i) {
            manager.addItem(new TimeEntry(tasks[1].getId(), cal1.getTime(), cal2.getTime(), UUID.randomUUID()));
        }
        date = new Date();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        date = new Date();
        cal3.setTime(date);
        cal3.add(Calendar.HOUR_OF_DAY, 2);
        cal4.setTime(date);
        cal4.add(Calendar.HOUR_OF_DAY, 4);
        for (int i = 0; i < 3; ++i) {
            manager.addItem(new TimeEntry(tasks[2].getId(), cal1.getTime(), cal2.getTime(), UUID.randomUUID()));
        }

        ArrayList<TimeEntryAggregator> list = manager.getGlobalStatistics(manager.getList().get(5).getStart(), cal2.getTime());
        assertEquals(2*3, list.get(0).getNbHours()/3600000);
        assertEquals(3*3, list.get(1).getNbHours()/3600000);
    }

    @Test(expected = RuntimeException.class)
    public void testGetGlobalStatisticsDateThrow() {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
        manager.getGlobalStatistics(cal.getTime(), date);
    }}