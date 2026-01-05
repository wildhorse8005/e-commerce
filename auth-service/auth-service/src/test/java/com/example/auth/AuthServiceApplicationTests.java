package com.example.auth;

import com.example.auth.application.PasswordVerifier;
import com.example.auth.domain.CredentialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AuthServiceApplicationTests {

    @MockBean
    CredentialRepository credentialRepository;

    @MockBean
    PasswordVerifier passwordVerifier;

    @Test
    void contextLoads() {
    }
}
