package controllers;

import models.IElement;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AListManager<T extends IElement> {
    protected ArrayList<T> list;

    protected AListManager() {
        this.list = new ArrayList<>();
    }

    public void addItem(T task) {
        if (list.stream().noneMatch(o -> o.getId() == task.getId())) {
            list.add(task);
        } else {
            throw new RuntimeException("Try adding a element with an ID already used.");
        }
    }

    public void deleteItem(UUID id) {
        list.removeIf(t -> t.getId().equals(id));
    }

    public ArrayList<T> getList() {
        return list;
    }
}

