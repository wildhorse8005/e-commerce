package com.example.auth.infrastructure.config;

import com.example.auth.application.service.CreateCredentialApplicationService;
import com.example.auth.application.service.LoginApplicationService;
import com.example.auth.application.port.PasswordVerifier;
import com.example.auth.application.port.TokenIssuer;
import com.example.auth.domain.CredentialRepository;
import com.example.auth.infrastructure.identity.IdentityClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    // =========================
    // APPLICATION SERVICES
    // =========================

    @Bean
    LoginApplicationService loginApplicationService(IdentityClient identityClient, CredentialRepository repository, PasswordVerifier passwordVerifier, TokenIssuer tokenIssuer) {
        return new LoginApplicationService(identityClient, repository, passwordVerifier, tokenIssuer);
    }

    @Bean
    CreateCredentialApplicationService createCredentialApplicationService(CredentialRepository repository, PasswordVerifier passwordVerifier) {
        return new CreateCredentialApplicationService(repository, passwordVerifier);
    }
}