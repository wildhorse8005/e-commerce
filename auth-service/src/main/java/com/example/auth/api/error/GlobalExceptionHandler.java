package com.example.auth.api.error;

import com.example.auth.application.exception.AccountLockedException;
import com.example.auth.application.exception.InvalidCredentialException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 401 – Invalid credential
    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ApiErrorResponse> invalidCredential(HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiErrorResponse(
                        401,
                        ErrorCode.INVALID_CREDENTIAL.name(),
                        "Invalid credentials",
                        req.getRequestURI()
                ));
    }

    // 423 – Account locked
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ApiErrorResponse> accountLocked(HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.LOCKED)
                .body(new ApiErrorResponse(
                        423,
                        ErrorCode.ACCOUNT_LOCKED.name(),
                        "Account is locked",
                        req.getRequestURI()
                ));
    }

    // 400 – JSON parse error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> badRequest(HttpServletRequest req) {
        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse(
                        400,
                        ErrorCode.INVALID_REQUEST.name(),
                        "Invalid request body",
                        req.getRequestURI()
                ));
    }

    // 400 – Validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validationError(HttpServletRequest req) {
        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse(
                        400,
                        ErrorCode.VALIDATION_ERROR.name(),
                        "Validation failed",
                        req.getRequestURI()
                ));
    }
}
