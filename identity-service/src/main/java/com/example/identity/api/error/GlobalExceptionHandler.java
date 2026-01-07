package com.example.identity.api.error;

import com.example.identity.application.exception.IdentityAlreadyExistsException;
import com.example.identity.application.exception.IdentityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdentityAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> alreadyExists(HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorResponse(409, ErrorCode.IDENTITY_ALREADY_EXISTS.name(), IdentityErrorMessages.IDENTITY_ALREADY_EXISTS, req.getRequestURI()));
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> notFound(HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(404, ErrorCode.IDENTITY_NOT_FOUND.name(), IdentityErrorMessages.IDENTITY_NOT_FOUND, req.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> badRequest(HttpServletRequest req) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(400, ErrorCode.INVALID_REQUEST.name(), IdentityErrorMessages.INVALID_REQUEST, req.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validationError(HttpServletRequest req) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(400, ErrorCode.VALIDATION_ERROR.name(), IdentityErrorMessages.VALIDATION_ERROR, req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> internalError(HttpServletRequest req, Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(500, ErrorCode.INTERNAL_ERROR.name(), IdentityErrorMessages.INTERNAL_ERROR, req.getRequestURI()));
    }
}