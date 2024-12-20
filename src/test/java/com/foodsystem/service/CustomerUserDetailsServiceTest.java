package com.foodsystem.service;

import com.foodsystem.entity.Admin;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.principal.AdminPrincipal;
import com.foodsystem.repo.IAdminRepo;
import com.foodsystem.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomerUserDetailsServiceTest {
    @Mock
    IAdminRepo adminRepo;

    private Admin admin;
    private String email;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        admin = new Admin();
        admin.setEmail("sanvii@example.com");
        admin.setPassword("sanvii@11");

        email = "sanvii@example.com";
    }

    @Test
    public void testLoadUserByUsername_Success() {
        when(adminRepo.findByEmail(email)).thenReturn(java.util.Optional.of(admin));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof AdminPrincipal);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(adminRepo.findByEmail(email)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            customUserDetailsService.loadUserByUsername(email);
        });
    }
}
