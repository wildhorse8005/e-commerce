package com.example.identity.application.exception;

public class IdentityAlreadyExistsException extends RuntimeException {

    public IdentityAlreadyExistsException(String email) {
        super("Identity already exists for email: " + email);
    }
}
