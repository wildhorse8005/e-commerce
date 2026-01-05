package com.example.identity.infrastructure;

import com.example.identity.domain.Identity;
import com.example.identity.domain.IdentityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaIdentityRepository implements IdentityRepository {

    private final SpringDataIdentityRepository delegate;

    public JpaIdentityRepository(SpringDataIdentityRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public Identity save(Identity identity) {
        return delegate.save(identity);
    }

    @Override
    public Optional<Identity> findById(UUID id) {
        return delegate.findById(id);
    }

    @Override
    public Optional<Identity> findByEmail(String email) {
        return delegate.findByEmail(email);
    }
}