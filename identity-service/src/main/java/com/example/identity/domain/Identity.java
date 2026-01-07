package com.example.identity.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "identities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class Identity {

    // ========================
    // FIELDS (PRIVATE – ALWAYS)
    // ========================

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentityStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    // ========================
    // CONSTRUCTORS
    // ========================

    /**
     * Required by JPA only.
     * DO NOT use directly.
     */
    protected Identity() {
    }

    private Identity(UUID id, String email) {
        this.id = id;
        this.email = email;
        this.status = IdentityStatus.ACTIVE;
        this.createdAt = Instant.now();
    }

    // ========================
    // FACTORY METHOD
    // ========================

    public static Identity create(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        return new Identity(UUID.randomUUID(), email);
    }

    // ========================
    // DOMAIN ACCESSORS
    // (NOT JavaBean getters)
    // ========================

    public UUID id() {
        return id;
    }

    public String email() {
        return email;
    }

    public IdentityStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    // ========================
    // DOMAIN BEHAVIOR
    // ========================

    public void activate() {
        this.status = IdentityStatus.ACTIVE;
    }

    public void suspend() {
        this.status = IdentityStatus.SUSPENDED;
    }

    public void delete() {
        this.status = IdentityStatus.DELETED;
    }
}