package com.example.sumup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class Task {
    private String name;
    private String command;
    private List<String> requiredTask = Collections.emptyList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @JsonIgnore
    public List<String> getRequiredTask() {
        return requiredTask;
    }

    @JsonProperty("requires")
    public void setRequiredTask(List<String> requiredTask) {
        this.requiredTask = requiredTask;
    }
}
