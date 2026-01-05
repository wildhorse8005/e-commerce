package com.example.auth.application;

public interface PasswordVerifier {
    boolean matches(String raw, String hashed);
}
