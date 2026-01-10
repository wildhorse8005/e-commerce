package com.example.auth.application.port;

import java.util.UUID;

public interface TokenIssuer {
    String issue(UUID identityId);
}

