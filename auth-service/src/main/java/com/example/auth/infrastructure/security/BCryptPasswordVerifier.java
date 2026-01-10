package com.example.auth.infrastructure.security;

import com.example.auth.application.port.PasswordVerifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordVerifier implements PasswordVerifier {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}

