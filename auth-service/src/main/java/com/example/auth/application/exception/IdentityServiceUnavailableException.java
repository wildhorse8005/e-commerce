package com.example.auth.application.exception;

/**
 * Thrown when Identity service is unreachable or returns unexpected error.
 * This represents a SYSTEM failure, not a user error.
 */
public class IdentityServiceUnavailableException extends RuntimeException {

    public IdentityServiceUnavailableException() {
        super();
    }

    public IdentityServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
