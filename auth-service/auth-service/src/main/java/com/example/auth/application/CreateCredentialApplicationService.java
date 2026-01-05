package com.example.auth.application;

import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;
import com.example.auth.infrastructure.security.BCryptPasswordVerifier;

import java.util.UUID;

public class CreateCredentialApplicationService {

    private final CredentialRepository repository;
    private final BCryptPasswordVerifier passwordVerifier;

    public CreateCredentialApplicationService(
            CredentialRepository repository,
            BCryptPasswordVerifier passwordVerifier
    ) {
        this.repository = repository;
        this.passwordVerifier = passwordVerifier;
    }

    public void create(UUID identityId, String rawPassword) {
        String hash = passwordVerifier.hash(rawPassword);
        Credential credential = new Credential(identityId, hash);
        repository.save(credential);
    }
}
