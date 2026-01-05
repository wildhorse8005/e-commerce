package com.example.auth.domain;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepository {

    Optional<Credential> findByIdentityId(UUID identityId);

    Credential save(Credential credential);
}
