package com.example.auth.infrastructure;

import com.example.auth.application.CreateCredentialApplicationService;
import com.example.auth.application.LoginApplicationService;
import com.example.auth.application.PasswordVerifier;
import com.example.auth.application.token.TokenIssuer;
import com.example.auth.domain.CredentialRepository;
import com.example.auth.infrastructure.identity.IdentityClient;
import com.example.auth.infrastructure.security.BCryptPasswordVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    LoginApplicationService loginApplicationService(
            IdentityClient identityClient,
            CredentialRepository repository,
            PasswordVerifier passwordVerifier,
            TokenIssuer tokenIssuer
    ) {
        return new LoginApplicationService(identityClient, repository, passwordVerifier, tokenIssuer);
    }

    @Bean
    CreateCredentialApplicationService createCredentialApplicationService(
            CredentialRepository repository,
            BCryptPasswordVerifier verifier
    ) {
        return new CreateCredentialApplicationService(repository, verifier);
    }
}
