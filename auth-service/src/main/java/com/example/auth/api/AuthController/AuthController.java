package com.example.auth.api.AuthController;

import com.example.auth.api.dto.CreateCredentialRequest;
import com.example.auth.api.dto.LoginRequest;
import com.example.auth.api.dto.LoginResponse;
import com.example.auth.application.service.CreateCredentialApplicationService;
import com.example.auth.application.service.LoginApplicationService;
import com.example.auth.application.service.LoginResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginApplicationService loginService;
    private final CreateCredentialApplicationService createCredentialService;

    public AuthController(
            LoginApplicationService loginService,
            CreateCredentialApplicationService createCredentialService
    ) {
        this.loginService = loginService;
        this.createCredentialService = createCredentialService;
    }

    // DEV / DEMO: CREATE CREDENTIAL
    @PostMapping("/credentials")
    public void createCredential(@Valid @RequestBody CreateCredentialRequest request) {
        createCredentialService.create(
                request.identityId(),
                request.password()
        );
    }

    // LOGIN
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginService.login(
                request.identityId(),
                request.password()
        );
        return new LoginResponse(result.accessToken());
    }
}