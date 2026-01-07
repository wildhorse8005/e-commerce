package com.example.auth.application;

import com.example.auth.application.exception.AccountLockedException;
import com.example.auth.application.exception.IdentityNotActiveException;
import com.example.auth.application.exception.IdentityNotFoundException;
import com.example.auth.application.exception.InvalidCredentialException;
import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;
import com.example.auth.infrastructure.identity.IdentityClient;
import com.example.auth.infrastructure.identity.dto.IdentityResponse;

import java.util.UUID;

public class LoginApplicationService {

    private final IdentityClient identityClient;
    private final CredentialRepository repository;
    private final PasswordVerifier passwordVerifier;

    public LoginApplicationService(
            IdentityClient identityClient,
            CredentialRepository repository,
            PasswordVerifier passwordVerifier
    ) {
        this.identityClient = identityClient;
        this.repository = repository;
        this.passwordVerifier = passwordVerifier;
    }

    public void login(UUID identityId, String rawPassword) {

        // Check identity existence
        IdentityResponse identity = identityClient.getIdentity(identityId)
                .orElseThrow(IdentityNotFoundException::new);

        // Check identity status
        if (!identity.statusEnum().isActive()) {
            throw new IdentityNotActiveException();
        }

        // Check credential
        Credential credential = repository.findByIdentityId(identityId)
                .orElseThrow(InvalidCredentialException::new);

        // Check account locked
        if (credential.isLocked()) {
            throw new AccountLockedException();
        }

        // Verify password
        if (!passwordVerifier.matches(rawPassword, credential.passwordHash())) {
            credential.recordFailedAttempt();
            repository.save(credential);
            throw new InvalidCredentialException();
        }

        // Reset failures on sucess
        credential.resetFailures();
        repository.save(credential);
    }
}
