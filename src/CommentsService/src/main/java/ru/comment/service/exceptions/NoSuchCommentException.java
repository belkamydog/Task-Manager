package ru.comment.service.exceptions;

public class NoSuchCommentException extends RuntimeException {
    public NoSuchCommentException(String message) {
        super("No comment or task: " + message);
    }
}
