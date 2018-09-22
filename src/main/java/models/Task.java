package models;

import java.util.UUID;

public class Task implements IElement {
    private UUID id;
    private String title;
    private String details;

    public Task(String title, String details, UUID id) {
        this.title = title;
        this.details = details;
        this.id = id;
    }

    public Task(String title, String details) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.details = details;
    }

    public Task(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.details = null;
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
}
