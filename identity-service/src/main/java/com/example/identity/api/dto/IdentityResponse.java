package com.example.identity.api.dto;

import com.example.identity.domain.Identity;

import java.time.Instant;
import java.util.UUID;

public class IdentityResponse {

    private UUID id;
    private String email;
    private String status;
    private Instant createdAt;

    public static IdentityResponse from(Identity identity) {
        IdentityResponse r = new IdentityResponse();
        r.id = identity.id();
        r.email = identity.email();
        r.status = identity.status().name();
        r.createdAt = identity.createdAt();
        return r;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}