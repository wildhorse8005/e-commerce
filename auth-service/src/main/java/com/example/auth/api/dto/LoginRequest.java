package com.example.auth.api.dto;

import java.util.UUID;

public record LoginRequest(
        UUID identityId,
        String password
) {}
