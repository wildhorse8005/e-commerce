package com.example.auth.application.port;

public interface PasswordVerifier {

    boolean matches(String rawPassword, String encodedPassword);

    String encode(String rawPassword);
}

