package com.example.auth.infrastructure.persistence;

import com.example.auth.domain.Credential;
import com.example.auth.domain.CredentialRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // ❗ CỰC KỲ QUAN TRỌNG
public class InMemoryCredentialRepository implements CredentialRepository {

    private final Map<UUID, Credential> store = new HashMap<>();

    @Override
    public Optional<Credential> findByIdentityId(UUID identityId) {
        return Optional.ofNullable(store.get(identityId));
    }

    @Override
    public Credential save(Credential credential) {
        store.put(credential.identityId(), credential);
        return credential;
    }
}
