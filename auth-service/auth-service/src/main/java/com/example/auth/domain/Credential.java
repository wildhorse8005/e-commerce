package com.example.auth.domain;

import java.time.Instant;
import java.util.UUID;

public class Credential {

    private final UUID identityId;
    private final String passwordHash;

    private int failedAttempts;
    private CredentialStatus status;
    private Instant lockedUntil;

    public Credential(UUID identityId, String passwordHash) {
        this.identityId = identityId;
        this.passwordHash = passwordHash;
        this.status = CredentialStatus.ACTIVE;
    }

    public UUID identityId() {
        return identityId;
    }

    public String passwordHash() {
        return passwordHash;
    }

    public boolean isLocked() {
        return status == CredentialStatus.LOCKED
                && lockedUntil != null
                && lockedUntil.isAfter(Instant.now());
    }

    public void recordFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= 5) {
            lockForMinutes(15);
        }
    }

    public void resetFailures() {
        failedAttempts = 0;
    }

    private void lockForMinutes(int minutes) {
        this.status = CredentialStatus.LOCKED;
        this.lockedUntil = Instant.now().plusSeconds(minutes * 60L);
    }
}
