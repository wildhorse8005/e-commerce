package com.example.auth.infrastructure.config;

import com.example.auth.application.token.TokenIssuer;
import com.example.auth.infrastructure.token.JwtTokenIssuer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    TokenIssuer tokenIssuer(
            @Value("${auth.jwt.issuer}") String issuer,
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.expiration-minutes}") long exp
    ) {
        return new JwtTokenIssuer(issuer, secret, exp);
    }
}
