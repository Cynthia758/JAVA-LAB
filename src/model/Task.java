package src.model;

// package model;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;

    public Task(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid task name");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}