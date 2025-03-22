package ru.gateway.exceptions;

public class AccessDenyException extends RuntimeException {
    public AccessDenyException() {
        super("Access denied");
    }
}
