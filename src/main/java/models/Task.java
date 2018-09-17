package models;

import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String details;
    private UUID type;

    public Task(String title, String details, UUID type, UUID id) {
        this.title = title;
        this.details = details;
        this.type = type;
        this.id = id;
    }

    public Task(String title, String details, UUID type) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.details = details;
        this.type = type;
    }

    public Task(String title, String details) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.details = details;
        this.type = null;
    }

    public Task(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.details = null;
        this.type = null;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UUID getType() {
        return type;
    }

    public void setType(UUID type) {
        this.type = type;
    }
}
