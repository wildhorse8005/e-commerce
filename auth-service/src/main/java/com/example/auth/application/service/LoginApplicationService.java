package com.example.auth.application.service;

import com.example.auth.api.error.ErrorCode;
import com.example.auth.application.port.PasswordVerifier;
import com.example.auth.application.exception.AccountLockedException;
import com.example.auth.application.exception.IdentityNotActiveException;
import com.example.auth.application.exception.IdentityNotFoundException;
import com.example.auth.application.exception.InvalidCredentialException;
import com.example.auth.application.port.TokenIssuer;
import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;
import com.example.auth.infrastructure.identity.IdentityClient;
import com.example.auth.infrastructure.identity.dto.IdentityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class LoginApplicationService {

    private static final Logger log = LoggerFactory.getLogger(LoginApplicationService.class);

    private final IdentityClient identityClient;
    private final CredentialRepository repository;
    private final PasswordVerifier passwordVerifier;
    private final TokenIssuer tokenIssuer;

    public LoginApplicationService(IdentityClient identityClient, CredentialRepository repository, PasswordVerifier passwordVerifier, TokenIssuer tokenIssuer) {
        this.identityClient = identityClient;
        this.repository = repository;
        this.passwordVerifier = passwordVerifier;
        this.tokenIssuer = tokenIssuer;
    }

    public LoginResult login(UUID identityId, String rawPassword) {

        log.info("event=AUTH_LOGIN_STARTED identityId={}", identityId);

        // Check identity existence
        IdentityResponse identity = identityClient.getIdentity(identityId).orElseThrow(IdentityNotFoundException::new);

        // Check identity status
        if (!identity.statusEnum().isActive()) {
            log.warn(
                    "event=AUTH_LOGIN_FAILED code={} identityId={}",
                    ErrorCode.IDENTITY_NOT_ACTIVE,
                    identityId
            );
            throw new IdentityNotActiveException();
        }

        // Check credential
        Credential credential = repository.findByIdentityId(identityId).orElseThrow(InvalidCredentialException::new);

        // Check account locked
        if (credential.isLocked()) {
            log.warn(
                    "event=AUTH_LOGIN_FAILED code={} identityId={}",
                    ErrorCode.ACCOUNT_LOCKED,
                    identityId
            );
            throw new AccountLockedException();
        }

        // Verify password
        if (!passwordVerifier.matches(rawPassword, credential.passwordHash())) {
            credential.recordFailedAttempt();
            repository.save(credential);
            log.warn(
                    "event=AUTH_LOGIN_FAILED code={} identityId={}",
                    ErrorCode.INVALID_CREDENTIAL,
                    identityId
            );
            throw new InvalidCredentialException();
        }

        // Reset failures on success
        credential.resetFailures();
        repository.save(credential);

        // Issue JWT
        String token = tokenIssuer.issue(identityId);
        log.info("event=AUTH_LOGIN_SUCCESS identityId={}", identityId);
        return new LoginResult(token);
    }
}
