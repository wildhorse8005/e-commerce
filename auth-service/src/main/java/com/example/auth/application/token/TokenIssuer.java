package com.example.auth.application.token;

import java.util.UUID;

public interface TokenIssuer {
    String issue(UUID identityId);
}

