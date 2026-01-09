package com.example.auth.infrastructure.token;

import com.example.auth.application.token.TokenIssuer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtTokenIssuer implements TokenIssuer {

    private final String issuer;
    private final byte[] secret;
    private final long expirationMinutes;

    public JwtTokenIssuer(String issuer, String secret, long expirationMinutes) {
        this.issuer = issuer;
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationMinutes = expirationMinutes;
    }

    @Override
    public String issue(UUID identityId) {
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer(issuer)
                .subject(identityId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();
    }
}
