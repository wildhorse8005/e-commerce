package com.example.auth.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCredentialRequest(
        @NotNull UUID identityId,
        @NotBlank String password
) {}

