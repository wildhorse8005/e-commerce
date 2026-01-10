package com.example.auth.infrastructure.config;

import com.example.auth.infrastructure.identity.IdentityClient;
import com.example.auth.infrastructure.identity.IdentityRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(IdentityServiceProperties.class)
public class IdentityClientConfig {

    private static final Logger log = LoggerFactory.getLogger(IdentityClientConfig.class);

    @Bean
    RestClient identityRestClient(IdentityServiceProperties props) {

        // ==== FAIL FAST CONFIG CHECK ====
        String baseUrl = props.getBaseUrl();

        if (baseUrl == null || baseUrl.isBlank()) {
            log.error("event=IDENTITY_CLIENT_CONFIG_ERROR reason=BASE_URL_MISSING");
            throw new IllegalStateException("identity.base-url must be configured");
        }

        log.info("event=IDENTITY_CLIENT_CONFIG_LOADED baseUrl={}", baseUrl);

        return RestClient.builder().baseUrl(props.getBaseUrl()).build();
    }

    @Bean
    IdentityClient identityClient(RestClient identityRestClient) {
        return new IdentityRestClient(identityRestClient);
    }
}
