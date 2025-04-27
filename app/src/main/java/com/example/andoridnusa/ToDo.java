package com.example.andoridnusa;

public class ToDo {
    private String id;
    private String task;
    private String deadline;

    public ToDo() {
    }

    public ToDo(String id, String task, String deadline) {
        this.id = id;
        this.task = task;
        this.deadline = deadline;
    }

    public String getId() { return id; }
    public String getTask() { return task; }
    public String getDeadline() { return deadline; }
}
