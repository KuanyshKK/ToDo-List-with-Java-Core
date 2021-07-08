package com.company.exam;

import java.util.ArrayList;
import java.util.List;

public class TaskCollection {
    List<Task> tasks = new ArrayList<>();

    public TaskCollection() {
        Task task = new Task();
        this.tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
