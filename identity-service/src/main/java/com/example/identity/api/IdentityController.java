package com.example.identity.api;

import com.example.identity.application.IdentityApplicationService;
import com.example.identity.domain.Identity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityApplicationService service;

    public IdentityController(IdentityApplicationService service) {
        this.service = service;
    }

    @PostMapping
    public Identity create(@RequestBody String email) {
        return service.createIdentity(email);
    }

    @GetMapping("/{id}")
    public Identity get(@PathVariable UUID id) {
        return service.getIdentity(id);
    }

    @PostMapping("/{id}/suspend")
    public Identity suspend(@PathVariable UUID id) {
        return service.suspendIdentity(id);
    }

    @PostMapping("/{id}/activate")
    public Identity activate(@PathVariable UUID id) {
        return service.activateIdentity(id);
    }
}