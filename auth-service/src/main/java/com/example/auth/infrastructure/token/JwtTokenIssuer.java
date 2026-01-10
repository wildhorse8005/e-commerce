package com.example.auth.infrastructure.token;

import com.example.auth.application.port.TokenIssuer;
import com.example.auth.application.exception.InternalAuthException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtTokenIssuer implements TokenIssuer {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenIssuer.class);

    private final String issuer;
    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtTokenIssuer(String issuer, String secret, long expirationMinutes) {

        // ==== FAIL FAST CONFIG CHECK ====
        if (secret == null || secret.isBlank()) {
            log.error("event=JWT_INIT_FAILED reason=SECRET_MISSING");
            throw new IllegalStateException("JWT secret is missing");
        }

        if (secret.length() < 32) {
            log.error(
                    "event=JWT_INIT_FAILED reason=SECRET_TOO_SHORT length={}",
                    secret.length()
            );
            throw new IllegalStateException("JWT secret must be at least 32 characters");
        }

        this.issuer = issuer;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
        log.info(
                "event=JWT_ISSUER_INITIALIZED issuer={} expirationMinutes={}",
                issuer,
                expirationMinutes
        );
    }

    @Override
    public String issue(UUID identityId) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(expirationMinutes * 60);

        try {
            String token = Jwts.builder()
                    .issuer(issuer)
                    .subject(identityId.toString())
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(expiry))
                    .signWith(secretKey)
                    .compact();

            // ===== SECURITY AUDIT LOG =====
            log.info(
                    "event=JWT_TOKEN_ISSUED type=ACCESS identityId={} expiresAt={}",
                    identityId,
                    expiry
            );

            return token;

        } catch (Exception ex) {
            // ===== SYSTEM FAILURE =====
            log.error(
                    "event=JWT_TOKEN_ISSUE_FAILED identityId={} exception={}",
                    identityId,
                    ex.getClass().getSimpleName()
            );
            throw new InternalAuthException();
        }
    }
}
