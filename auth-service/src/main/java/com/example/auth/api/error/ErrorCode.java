package com.example.auth.api.error;

public enum ErrorCode {

    // ===== AUTH =====
    INVALID_CREDENTIAL,
    ACCOUNT_LOCKED,

    // ===== IDENTITY (AUTH VIEW) =====
    IDENTITY_NOT_FOUND,
    IDENTITY_NOT_ACTIVE,
    IDENTITY_SERVICE_UNAVAILABLE,

    // ===== REQUEST =====
    INVALID_REQUEST,
    VALIDATION_ERROR,

    // ===== SYSTEM =====
    INTERNAL_ERROR
}
