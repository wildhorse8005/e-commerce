package com.example.auth.infrastructure.identity;

import com.example.auth.infrastructure.identity.dto.IdentityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.*;

import java.util.Optional;
import java.util.UUID;

public class IdentityRestClient implements IdentityClient {

    private static final Logger log = LoggerFactory.getLogger(IdentityRestClient.class);

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

        } catch (HttpClientErrorException.NotFound ex) {
            // 404 → identity not found (business case)
            return Optional.empty();

        } catch (HttpClientErrorException ex) {
            // 4xx → client / contract issue
            log.warn(
                    "event=IDENTITY_CALL_FAILED status={} identityId={}",
                    ex.getStatusCode().value(),
                    identityId
            );
            throw ex;

        } catch (HttpServerErrorException ex) {
            // 5xx → identity-service error
            log.error(
                    "event=IDENTITY_SERVICE_ERROR status={} identityId={}",
                    ex.getStatusCode().value(),
                    identityId
            );
            throw ex;

        } catch (ResourceAccessException ex) {
            // timeout / connection refused
            log.error(
                    "event=IDENTITY_SERVICE_UNAVAILABLE identityId={}",
                    identityId
            );
            throw ex;
        }
    }
}