package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)  // Ensures Mockito annotations are processed
public class CustomerServiceImplTest
{
    @Mock
    private ICustomerRepo repo; // Mocking the repository

    @InjectMocks
    private CustomerServiceImpl customerService; // Injecting mocks into the service

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setCustomerName("John Doe");
        customer.setPassword("password123");
        customer.setStatus(true); // Initial status as active (true)
    }

    // Test Case 1: Customer is found and successfully soft-deleted
    @Test
    public void testSoftDeleteCustomer_Success() {
        // Arrange: Mock the repository to return a customer when searching by email
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(customer));

        // Act: Call the service method
        ApiResponse response = customerService.softDeleteCustomer("customer@example.com");

        // Assert: Verify the response message, code, and status
        assertEquals("Successfully customer removed", response.getMsg());
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());

        // Verify the repository's save method is called once
        verify(repo, times(1)).save(customer);

        // Verify that the customer's status was updated to false
        assertFalse(customer.getStatus());
    }

    // Test Case 2: Customer is not found and ResourceNotFoundExceptions is thrown
    @Test
    public void testSoftDeleteCustomer_CustomerNotFound() {
        // Arrange: Mock the repository to return an empty Optional (customer not found)
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert: Check if the exception is thrown
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            customerService.softDeleteCustomer("nonexistent@example.com");
        });
    }

}
