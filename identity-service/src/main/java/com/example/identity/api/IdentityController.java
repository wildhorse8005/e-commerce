package com.example.identity.api;

import com.example.identity.api.dto.CreateIdentityRequest;
import com.example.identity.api.dto.IdentityResponse;
import com.example.identity.application.IdentityApplicationService;
import com.example.identity.domain.Identity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityApplicationService service;

    public IdentityController(IdentityApplicationService service) {
        this.service = service;
    }

    /**
     * Create new identity
     * POST /identities
     */
    @PostMapping
    public ResponseEntity<IdentityResponse> create(@RequestBody @Valid CreateIdentityRequest request) {
        Identity identity = service.createIdentity(request.email());

        return ResponseEntity.status(HttpStatus.CREATED).body(IdentityResponse.from(identity));
    }

    /**
     * Get identity by id
     * GET /identities/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<IdentityResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(
                IdentityResponse.from(service.getIdentity(id))
        );
    }

    /**
     * Suspend identity
     * POST /identities/{id}/suspend
     */
    @PostMapping("/{id}/suspend")
    public ResponseEntity<IdentityResponse> suspend(@PathVariable UUID id) {
        return ResponseEntity.ok(
                IdentityResponse.from(service.suspendIdentity(id))
        );
    }

    /**
     * Activate identity
     * POST /identities/{id}/activate
     */
    @PostMapping("/{id}/activate")
    public ResponseEntity<IdentityResponse> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(
                IdentityResponse.from(service.activateIdentity(id))
        );
    }
}
