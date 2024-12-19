package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.service.impl.CustomerServiceImpl;
import com.foodsystem.service.impl.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest
{
    @Mock
    private ICustomerRepo repo;

    @InjectMocks
    private CustomerServiceImpl customerService;



    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;



    private Customer customer;



    @Mock
    private BCryptPasswordEncoder encoder;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setEmail("customer@example.com");
        customer.setCustomerName("John Doe");
        customer.setPassword("password123");
        customer.setStatus(true);
        customer.setStatus(true);
    }

    @Test
    public void testSoftDeleteCustomer_Success() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(customer));

        ApiResponse response = customerService.softDeleteCustomer("customer@example.com");

        assertEquals("Successfully customer removed", response.getMsg());
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());

        verify(repo, times(1)).save(customer);

        assertFalse(customer.getStatus());
    }

    @Test
    public void testSoftDeleteCustomer_CustomerNotFound() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            customerService.softDeleteCustomer("nonexistent@example.com");
        });
    }


    @Test
    public void testPermanentDeleteCustomer_Success() {
        String email = "test@example.com";
        Customer mockCustomer = new Customer();
        mockCustomer.setEmail(email);


        when(repo.findByEmail(email)).thenReturn(Optional.of(mockCustomer));

        ApiResponse response = customerService.permanentDeleteCustomer(email);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());

        verify(repo, times(1)).deleteByEmail(email);
    }
    @Test
    public void testPermanentDeleteCustomer_CustomerNotFound() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class,()-> customerService.permanentDeleteCustomer("test@example.com"));
        verify(repo,never()).deleteByEmail(anyString());
    }



    @Test
    void testAddCustomer_WhenEmailIsUnique() {
        when(repo.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode("password123")).thenReturn("encodedPassword");
        ApiResponse response = customerService.addCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getCode());
        assertTrue(response.getSuccess());
    }

    @Test
    void testAddCustomer_WhenEmailIsNotUnique() {
        when(repo.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        ResourceNotFoundExceptions exception = assertThrows(ResourceNotFoundExceptions.class, () -> {
            customerService.addCustomer(customer);
        });
       assertEquals(" customer email should be Unique", exception.getMessage());
    }
}
