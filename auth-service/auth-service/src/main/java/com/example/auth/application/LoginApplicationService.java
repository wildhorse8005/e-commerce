package com.example.auth.application;

import com.example.auth.application.exception.AccountLockedException;
import com.example.auth.application.exception.InvalidCredentialException;
import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;

import java.util.UUID;

public class LoginApplicationService {

    private final CredentialRepository repository;
    private final PasswordVerifier passwordVerifier;

    public LoginApplicationService(
            CredentialRepository repository,
            PasswordVerifier passwordVerifier
    ) {
        this.repository = repository;
        this.passwordVerifier = passwordVerifier;
    }

    public void login(UUID identityId, String rawPassword) {

        Credential credential = repository.findByIdentityId(identityId)
                .orElseThrow(InvalidCredentialException::new);

        if (credential.isLocked()) {
            throw new AccountLockedException();
        }

        if (!passwordVerifier.matches(rawPassword, credential.passwordHash())) {
            credential.recordFailedAttempt();
            repository.save(credential);
            throw new InvalidCredentialException();
        }

        credential.resetFailures();
        repository.save(credential);
    }
}
