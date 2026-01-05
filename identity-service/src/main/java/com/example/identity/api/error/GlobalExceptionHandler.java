package com.example.identity.api.error;

import com.example.identity.application.exception.IdentityAlreadyExistsException;
import com.example.identity.application.exception.IdentityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdentityAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleIdentityExists(
            IdentityAlreadyExistsException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", "IDENTITY_ALREADY_EXISTS",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            IdentityNotFoundException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", "IDENTITY_NOT_FOUND",
                        "message", ex.getMessage()
                ));
    }
}
