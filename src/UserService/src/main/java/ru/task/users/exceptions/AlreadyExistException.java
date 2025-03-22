package ru.task.users.exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message) {
        super(message + " is already exist");
    }
}
