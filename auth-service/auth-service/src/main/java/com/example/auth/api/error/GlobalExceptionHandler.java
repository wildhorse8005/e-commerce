package com.example.auth.api.error;

import com.example.auth.application.exception.AccountLockedException;
import com.example.auth.application.exception.InvalidCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<?> handleInvalidCredential() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", "INVALID_CREDENTIAL"
                ));
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<?> handleAccountLocked() {
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", "ACCOUNT_LOCKED"
                ));
    }
}
