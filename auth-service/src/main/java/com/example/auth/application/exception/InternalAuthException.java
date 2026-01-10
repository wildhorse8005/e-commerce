package com.example.auth.application.exception;

/**
 * System-level exception.
 * Used when auth service encounters internal failure.
 */
public class InternalAuthException extends RuntimeException {

    public InternalAuthException() {
        super("Internal authentication error");
    }
}
