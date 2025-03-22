package ru.task.service.exceptions;

public class AccessDenyException  extends RuntimeException {
    public AccessDenyException() {
        super("Access denied, you are not owner or executor to access this resource");
    }
}
