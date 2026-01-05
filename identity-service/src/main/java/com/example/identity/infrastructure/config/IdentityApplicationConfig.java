package com.example.identity.infrastructure.config;

import com.example.identity.application.IdentityApplicationService;
import com.example.identity.domain.IdentityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentityApplicationConfig {

    @Bean
    public IdentityApplicationService identityApplicationService(
            IdentityRepository repository
    ) {
        return new IdentityApplicationService(repository);
    }
}
