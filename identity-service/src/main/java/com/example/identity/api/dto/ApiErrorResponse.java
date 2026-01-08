package com.example.identity.api.dto;

public record ApiErrorResponse(
        int status,
        String code,
        String message,
        String path
) {}