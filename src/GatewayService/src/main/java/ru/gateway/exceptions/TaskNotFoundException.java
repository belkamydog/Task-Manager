package ru.gateway.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super("Task with id: " + message + " not found");
    }
}
