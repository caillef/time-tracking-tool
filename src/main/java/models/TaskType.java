package models;

import java.util.UUID;

public class TaskType {
    private UUID id;
    private String title;

    public TaskType(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public TaskType(UUID id, String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
