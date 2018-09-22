package controllers;

import models.TimeEntry;
import models.TimeEntryAggregator;

import java.util.*;
import java.util.stream.Collectors;

public class EntriesManager extends AListManager<TimeEntry> {
    public EntriesManager() {
        super();
    }

    public void changeTask(UUID entryId, UUID taskId) {
        // Get a specific task
        Optional<TimeEntry> optTask = list.stream()
                .filter(t -> t.getId() == entryId).findFirst();
        TimeEntry timeEntry = optTask.orElse(null);

        // if it exists, change the taskID
        if (timeEntry != null) {
            timeEntry.setTaskId(taskId);
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
                optTask = new TimeEntryAggregator(t1.getTaskId());
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
}
