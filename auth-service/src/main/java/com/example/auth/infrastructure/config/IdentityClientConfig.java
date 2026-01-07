package com.example.auth.infrastructure.config;

import com.example.auth.infrastructure.identity.IdentityClient;
import com.example.auth.infrastructure.identity.IdentityRestClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(IdentityServiceProperties.class)
public class IdentityClientConfig {

    @Bean
    RestClient identityRestClient(IdentityServiceProperties props) {
        return RestClient.builder()
                .baseUrl(props.getBaseUrl())
                .build();
    }

    @Bean
    IdentityClient identityClient(RestClient identityRestClient) {
        return new IdentityRestClient(identityRestClient);
    }
}
