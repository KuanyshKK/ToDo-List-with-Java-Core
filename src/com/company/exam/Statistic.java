package com.company.exam;

public class Statistic {
    private int taskNew;
    private int taskDone;

    public Statistic(int taskNew, int taskDone) {
        this.taskNew = taskNew;
        this.taskDone = taskDone;
    }

    public int getTaskNew() {
        return taskNew;
    }

    public void setTaskNew(int taskNew) {
        this.taskNew = taskNew;
    }

    public int getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(int taskDone) {
        this.taskDone = taskDone;
    }
}
