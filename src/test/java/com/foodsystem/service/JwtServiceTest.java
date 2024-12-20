package com.foodsystem.service;

import com.foodsystem.service.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtServiceTest {

    private JwtService jwtService;

    @InjectMocks
    private JwtService service;

    private String validToken;
    private String invalidToken;

    private Date expirationDate;
    private String email;
    @BeforeEach
    public void setUp() {
         email = "ishika11@.com";
        jwtService = new JwtService();
    }


    @Test
    void testGenerateToken_ValidEmail() {
        String token = jwtService.generateToken(email);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }


    @Test
    void testExtractUserEmail() {
        String token = jwtService.generateToken(email);

        String extractedEmail = jwtService.extractUserEmail(token);

        assertNotNull(extractedEmail);
        assertEquals(email, extractedEmail);
    }

    @Test
    void testExtractClaim() {
        String token = jwtService.generateToken(email);

        String subject = jwtService.extractClaim(token, Claims::getSubject);

        assertNotNull(subject);
        assertEquals(email, subject);
    }


    @Test
    void testExtractAllClaims() {
        String token = jwtService.generateToken(email);

        Claims claims = jwtService.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals(email, claims.getSubject());
    }

    @Test
    void testValidateToken() {
        String email = "user@example.com";
        String token = jwtService.generateToken(email);

        UserDetails userDetails = new User(email, "", Collections.emptyList());

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testIsTokenExpired_NotExpired() {
        String token = jwtService.generateToken(email);
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    void testExtractExpiration() {
        String token = jwtService.generateToken(email);

        Date expirationDate = jwtService.extractExpiration(token);

        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testGetKey() {
        SecretKey key = jwtService.getKey();

        assertNotNull(key);
        assertTrue(key.getEncoded().length > 0);
    }

}
