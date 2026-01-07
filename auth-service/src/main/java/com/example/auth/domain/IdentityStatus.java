package com.example.auth.domain;

public enum IdentityStatus {
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}

