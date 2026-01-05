package com.example.identity.infrastructure;

import com.example.identity.domain.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface SpringDataIdentityRepository extends JpaRepository<Identity, UUID> {

    Optional<Identity> findByEmail(String email);
}