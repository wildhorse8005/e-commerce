package com.example.auth.api.error;

import com.example.auth.application.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ===================== AUTH =====================

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ApiError> invalidCredential(InvalidCredentialException ex, HttpServletRequest req) {
        return response(ErrorCode.INVALID_CREDENTIAL, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ApiError> accountLocked(AccountLockedException ex, HttpServletRequest req) {
        return response(ErrorCode.ACCOUNT_LOCKED, HttpStatus.LOCKED);
    }

    // ===================== IDENTITY =====================

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<ApiError> identityNotFound(IdentityNotFoundException ex, HttpServletRequest req) {
        return response(ErrorCode.IDENTITY_NOT_FOUND, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IdentityNotActiveException.class)
    public ResponseEntity<ApiError> identityNotActive(IdentityNotActiveException ex, HttpServletRequest req) {
        return response(ErrorCode.IDENTITY_NOT_ACTIVE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IdentityServiceUnavailableException.class)
    public ResponseEntity<ApiError> identityServiceUnavailable(IdentityServiceUnavailableException ex, HttpServletRequest req) {
        return response(ErrorCode.IDENTITY_SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // ===================== REQUEST =====================

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> invalidRequest(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return response(ErrorCode.INVALID_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validationError(MethodArgumentNotValidException ex, HttpServletRequest req) {
        return response(ErrorCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> resourceNotFound(NoResourceFoundException ex, HttpServletRequest req) {
        log.warn("AUTH_ROUTE_NOT_FOUND path={}", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError(ErrorCode.INVALID_REQUEST));
    }

    // ===================== FALLBACK =====================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> internalError(Exception ex, HttpServletRequest req) {
        log.error(
                "AUTH_INTERNAL_ERROR path={} exception={}",
                req.getRequestURI(),
                ex.getClass().getSimpleName()
        );
        return response(ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalAuthException.class)
    public ResponseEntity<ApiError> internalAuthError(HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(ErrorCode.INTERNAL_ERROR));
    }

    // ===================== HELPERS =====================

    private ResponseEntity<ApiError> response(ErrorCode code, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiError(code));
    }
}