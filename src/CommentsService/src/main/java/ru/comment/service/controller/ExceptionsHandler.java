package ru.comment.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.comment.service.exceptions.NoSuchCommentException;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(NoSuchCommentException.class)
    public ResponseEntity<?> handleUserNotFoundException(NoSuchCommentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
