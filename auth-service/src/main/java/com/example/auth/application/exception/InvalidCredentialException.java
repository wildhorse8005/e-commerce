package com.example.auth.application.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException() {
        super("Invalid credentials");
    }
}
