package com.oocl.training.advice;

import com.oocl.training.exception.InvalidCompanyException;
import com.oocl.training.exception.InvalidEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<?> handleInvalidEmployeeException(InvalidEmployeeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "Invalid employee.",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(InvalidCompanyException.class)
    public ResponseEntity<?> handleInvalidCompanyException(InvalidCompanyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "message", "Invalid company.",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "message", "Internal server error.",
                        "error", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                ));
    }
}
