package com.example.auth.api.dto;

import java.util.UUID;

public record CreateCredentialRequest(
        UUID identityId,
        String password
) {}

