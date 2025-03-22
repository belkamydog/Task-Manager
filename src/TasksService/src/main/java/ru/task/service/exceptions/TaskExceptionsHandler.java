package ru.task.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaskExceptionsHandler {
    @ExceptionHandler(NoSuchTaskException.class)
    public ResponseEntity<?> handleUserNotFoundException(NoSuchTaskException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(AccessDenyException.class)
    public ResponseEntity<?> handleAccessDenyException(AccessDenyException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
