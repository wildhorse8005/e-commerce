package com.example.auth.infrastructure.identity.dto;

import com.example.auth.domain.IdentityStatus;

import java.util.UUID;

public record IdentityResponse(
        UUID id,
        String status
) {
    public IdentityStatus statusEnum() {
        return IdentityStatus.valueOf(status);
    }
}

