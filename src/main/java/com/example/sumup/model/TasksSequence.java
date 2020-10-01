package com.example.sumup.model;

import java.util.Collections;
import java.util.List;

public class TasksSequence {
    private List<Task> tasks = Collections.emptyList();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
