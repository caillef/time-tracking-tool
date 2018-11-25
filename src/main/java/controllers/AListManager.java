package controllers;

import models.IElement;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AListManager<T extends IElement> {
    private final DatabaseManager db;
    protected ArrayList<T> list;

    protected AListManager(DatabaseManager db) {
        this.db = db;
        this.list = new ArrayList<>();
    }

    public AListManager() {
        this.db = null;
        this.list = new ArrayList<>();
    }

    public void addItem(T elem) {
        if (list.stream().noneMatch(o -> o.getId() == elem.getId())) {
            list.add(elem);
            if (this.db != null)
                db.AddItem(elem);
        } else {
            throw new RuntimeException("Try adding a element with an ID already used.");
        }
    }

    public <T extends IElement> void deleteItem(T elem) {
        list.removeIf(t -> t.getId().equals(elem.getId()));
        if (this.db != null)
            db.DeleteItem(elem);
    }

    public T getById(UUID id) {
        return list.stream().filter(t-> t.getId() == id).findFirst().orElse(null);
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void clear() {
        list.clear();
    }
}

