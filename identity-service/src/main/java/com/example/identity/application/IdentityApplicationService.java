package com.example.identity.application;

import com.example.identity.application.exception.IdentityAlreadyExistsException;
import com.example.identity.application.exception.IdentityNotFoundException;
import com.example.identity.domain.Identity;
import com.example.identity.domain.IdentityRepository;

import java.util.UUID;

public class IdentityApplicationService {

    private final IdentityRepository repository;

    public IdentityApplicationService(IdentityRepository repository) {
        this.repository = repository;
    }

    public Identity createIdentity(String email) {
        repository.findByEmail(email).ifPresent(existing -> {
            throw new IdentityAlreadyExistsException(email);
        });

        Identity identity = Identity.create(email);
        return repository.save(identity);
    }

    public Identity suspendIdentity(UUID id) {
        Identity identity = repository.findById(id).orElseThrow(() -> new IdentityNotFoundException(id));

        identity.suspend();
        return repository.save(identity);
    }

    public Identity activateIdentity(UUID id) {
        Identity identity = repository.findById(id).orElseThrow(() -> new IdentityNotFoundException(id));

        identity.activate();
        return repository.save(identity);
    }

    public Identity getIdentity(UUID id) {
        return repository.findById(id).orElseThrow(() -> new IdentityNotFoundException(id));
    }
}