package com.example.identity.api.error;

public enum ErrorCode {

    // AUTH
    INVALID_CREDENTIAL,
    ACCOUNT_LOCKED,
    IDENTITY_SUSPENDED,

    // IDENTITY
    IDENTITY_NOT_FOUND,
    IDENTITY_ALREADY_EXISTS,

    // REQUEST
    INVALID_REQUEST,
    VALIDATION_ERROR,

    // SYSTEM
    INTERNAL_ERROR
}
