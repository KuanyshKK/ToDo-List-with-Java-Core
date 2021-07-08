package com.company.exam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String id;
    private String heading;
    private String executorName;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;
    private String description;
    private String status;



    public Task(String heading, String executorName, String description) {
        this.id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
        this.heading = heading;
        this.executorName = executorName;
        this.dateOfStart = LocalDate.now();
        this.description = description;
        this.status = "new";
    }

    public Task() {
    }

    public Task(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public LocalDate getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(LocalDate dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public LocalDate getDateOfFinish() {
        return dateOfFinish;
    }

    public void setDateOfFinish(LocalDate dateOfFinish) {
        this.dateOfFinish = dateOfFinish;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
