package com.oocl.training.model;

public class Todo {
    private int id;
    private String title;
    private String status;

    public Todo() {

    }

    public Todo(int id, String title, String status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
