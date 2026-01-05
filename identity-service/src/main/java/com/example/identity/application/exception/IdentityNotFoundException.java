package com.example.identity.application.exception;

import java.util.UUID;

public class IdentityNotFoundException extends RuntimeException {

    public IdentityNotFoundException(UUID id) {
        super("Identity not found with id: " + id);
    }
}
