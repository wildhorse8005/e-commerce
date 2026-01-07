package com.example.auth.infrastructure.identity;

import com.example.auth.infrastructure.identity.dto.IdentityResponse;

import java.util.Optional;
import java.util.UUID;

public interface IdentityClient {

    Optional<IdentityResponse> getIdentity(UUID identityId);
}
