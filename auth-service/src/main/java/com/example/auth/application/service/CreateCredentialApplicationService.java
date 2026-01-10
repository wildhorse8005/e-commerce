package com.example.auth.application.service;

import com.example.auth.application.port.PasswordVerifier;
import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;

import java.util.UUID;

public class CreateCredentialApplicationService {

    private final CredentialRepository repository;
    private final PasswordVerifier passwordVerifier;

    public CreateCredentialApplicationService(
            CredentialRepository repository,
            PasswordVerifier passwordVerifier
    ) {
        this.repository = repository;
        this.passwordVerifier = passwordVerifier;
    }

    public void create(UUID identityId, String rawPassword) {
        String encoded = passwordVerifier.encode(rawPassword);
        Credential credential = new Credential(identityId, encoded);
        repository.save(credential);
    }
}
