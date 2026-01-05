package com.example.auth.infrastructure.security;

import com.example.auth.application.PasswordVerifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordVerifier implements PasswordVerifier {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String raw, String hashed) {
        return encoder.matches(raw, hashed);
    }

    public String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
