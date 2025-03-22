package ru.task.service.exceptions;

public class NoSuchTaskException extends RuntimeException {
    public NoSuchTaskException(String message) {
        super("No such task: " + message);
    }
}
