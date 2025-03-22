package ru.gateway.controller;

import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class FeignErrorDecoder {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException e) {
        return new ResponseEntity<>(e.contentUTF8(), HttpStatusCode.valueOf(e.status()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleTokenExpException(Exception e) {
        return new ResponseEntity<>("Token expired", HttpStatus.UNAUTHORIZED);
    }
}
