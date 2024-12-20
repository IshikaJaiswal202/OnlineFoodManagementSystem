package com.foodsystem.service;

import com.foodsystem.entity.Admin;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.service.impl.AdminServiceImpl;
import com.foodsystem.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;


    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setEmail("ishika@.com");
        admin.setPassword("ishika123");
    }

    @Test
    void testVerifyForLogin_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(admin.getEmail())).thenReturn("mocked-jwt-token");

        String token = adminService.verifyForLogin(admin);

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
        verify(jwtService).generateToken(admin.getEmail());

    }

    @Test
    void testVerifyForLogin_Failure_InvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        ResourceNotFoundExceptions exception = assertThrows(ResourceNotFoundExceptions.class, () -> {
            adminService.verifyForLogin(admin);
        });

        assertEquals("Invalid email or password. Please try again.", exception.getMessage());
    }

    @Test
    void testVerifyForLogin_Failure_NotAuthenticated() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        ResourceNotFoundExceptions exception = assertThrows(ResourceNotFoundExceptions.class, () -> {
            adminService.verifyForLogin(admin);
        });

    }

    @Test
    void testVerifyForLogin_Failure_TokenGenerationError() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
    }
}
