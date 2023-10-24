package com.hong.api;

import com.hong.exception.CustomAuthException;
import com.hong.exception.CustomContentTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthException.class)
    public ResponseEntity<String> handleCustomException(CustomAuthException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: " + e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request! check args: " + e.getMessage());
    }

    @ExceptionHandler(CustomContentTypeException.class)
    public ResponseEntity<String> handleContentTypeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported content type");
    }
}

