package com.example.auth.infrastructure.identity;

import com.example.auth.application.exception.IdentityServiceUnavailableException;
import com.example.auth.infrastructure.identity.dto.IdentityResponse;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;
import java.util.UUID;

public class IdentityRestClient implements IdentityClient {

    private final RestClient restClient;

    public IdentityRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Optional<IdentityResponse> getIdentity(UUID identityId) {

        try {
            IdentityResponse response = restClient.get()
                    .uri(IdentityEndpoints.GET_IDENTITY_BY_ID, identityId)
                    .retrieve()
                    .body(IdentityResponse.class);

            return Optional.ofNullable(response);

        } catch (RestClientResponseException ex) {

            // 404 → Identity does not exist (BUSINESS CASE)
            if (ex.getStatusCode().value() == 404) {
                return Optional.empty();
            }

            // Other HTTP errors (5xx, 401, 403, etc.)
            throw new IdentityServiceUnavailableException(ex);

        } catch (ResourceAccessException ex) {
            // Connection refused / timeout / DNS error
            throw new IdentityServiceUnavailableException(ex);
        }
    }
}