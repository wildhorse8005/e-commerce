package com.example.identity.domain;

import java.util.Optional;
import java.util.UUID;

public interface IdentityRepository {

    Identity save(Identity identity);

    Optional<Identity> findById(UUID id);

    Optional<Identity> findByEmail(String email);
}