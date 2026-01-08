package com.example.identity.api.dto;

import com.example.identity.domain.Identity;

import java.time.Instant;
import java.util.UUID;

public record IdentityResponse(
        UUID id,
        String email,
        String status,
        Instant createdAt
) {
    public static IdentityResponse from(Identity identity) {
        return new IdentityResponse(
                identity.getId(),
                identity.getEmail(),
                identity.getStatus().name(),
                identity.getCreatedAt()
        );
    }
}
