package com.example.auth.api;

import com.example.auth.api.dto.CreateCredentialRequest;
import com.example.auth.api.dto.LoginRequest;
import com.example.auth.api.dto.LoginResponse;
import com.example.auth.application.CreateCredentialApplicationService;
import com.example.auth.application.LoginApplicationService;
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

    // 🔹 DEV / DEMO: CREATE CREDENTIAL
    @PostMapping("/credentials")
    public void createCredential(@RequestBody CreateCredentialRequest request) {
        createCredentialService.create(
                request.identityId(),
                request.password()
        );
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        loginService.login(
                request.identityId(),
                request.password()
        );
        return new LoginResponse("LOGIN_SUCCESS");
    }
}