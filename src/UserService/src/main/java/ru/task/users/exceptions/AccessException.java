package ru.task.users.exceptions;

public class AccessException extends RuntimeException {
    public AccessException() {
        super("Access denied exception");
    }
}
