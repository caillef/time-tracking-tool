package controllers;

import models.TimeEntry;
import models.TimeEntryAggregator;

import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

public class EntriesManager extends AListManager<TimeEntry> {
    private DatabaseManager db;

    public EntriesManager(DatabaseManager db) {
        super(db);
        this.db = db;
    }

    public EntriesManager() {
        super();
        this.db = null;
    }

    public void changeTask(UUID entryId, UUID taskId) {
        // Get a specific task
        Optional<TimeEntry> optTask = list.stream()
                .filter(t -> t.getId() == entryId).findFirst();
        TimeEntry timeEntry = optTask.orElse(null);

        // if it exists, change the taskID
        if (timeEntry != null) {
            timeEntry.setTaskId(taskId);
            if (db != null)
                db.UpdateItem(timeEntry, null, taskId);
        }
    }

    private ArrayList<TimeEntryAggregator> getListStatistics(List<TimeEntry> list) {
        // Create empty list
        ArrayList<TimeEntryAggregator> globalList = new ArrayList<>();

        // For each time entry, add a new aggregator if it's a new task, and then compute the number of hours it represents
        list.forEach(t1 -> {
            TimeEntryAggregator optTask = globalList.stream()
                    .filter(t2 -> t1 != t2 && t1.getTaskId() == t2.getTaskId()).findFirst().orElse(null);
            if (optTask == null) {
                optTask = new TimeEntryAggregator(null, t1.getTaskId());
                globalList.add(optTask);
            }
            optTask.addNbHours(t1.getEnd().getTime() - t1.getStart().getTime());
        });
        return globalList;
    }

    public ArrayList<TimeEntryAggregator> getGlobalStatistics() {
        return getListStatistics(list);
    }

    public ArrayList<TimeEntryAggregator> getGlobalStatistics(Date start, Date end) throws RuntimeException {
        if (start.getTime() >= end.getTime()) {
            throw new RuntimeException("Start date is higher than end date");
        }
        return getListStatistics(list.stream()
                // Remove time entries outside of the range
                .filter(t -> {
                    long tstart = t.getStart().getTime();
                    long tend = t.getEnd().getTime();
                    return (tstart <= end.getTime() && tend >= start.getTime());
                })
                // Map to remove extra hours
                .map(t -> {
                    if (t.getStart().getTime() < start.getTime()) {
                        t.setStart(start);
                    }
                    if (t.getEnd().getTime() > end.getTime()) {
                        t.setEnd(end);
                    }
                    return t;
                })
                .collect(Collectors.toList()));
    }


    @Override
    public TimeEntry getById(UUID id) {
        if (db == null)
            return super.getById(id);
        else
            return db.GetEntry(id).orElse(null);
    }

    @Override
    public ArrayList<TimeEntry> getList() {
        if (db == null)
            return super.getList();
        else
            return db.GetEntries();
    }

    @Override
    public void clear() {
        if (db == null)
            super.clear();
        else
            db.ClearEntries();
    }
}
