package com.foodsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.service.ICustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminTestController {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ICustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setPassword("password123");
        customer.setCustomerName("John Doe");
    }

    @Test
    public void testAddCustomer_Success() throws Exception {
        // Arrange: Mocking the service method to return a successful ApiResponse
        ApiResponse apiResponse = new ApiResponse.Builder()
                .msg("Successfully added customer")
                .code(HttpStatus.CREATED)
                .success(true)
                .build();

        when(customerService.addCustomer(any(Customer.class))).thenReturn(apiResponse);

        // Act: Calling the endpoint using MockMvc
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/admin/addCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())  // Status code 201
                .andExpect(content().string("Successfully added customer"));

        // Verify the service method was called once with any Customer object
        verify(customerService, times(1)).addCustomer(any(Customer.class));
    }

        // Test for adding a customer where email already exists
        @Test
        public void testAddCustomer_EmailAlreadyExists() throws Exception {
            // Arrange: Mocking the service method to throw an exception if the email already exists
            when(customerService.addCustomer(customer)).thenThrow(new ResourceNotFoundExceptions("customer email should be Unique"));

            // Act & Assert: Checking if the exception message is returned with the correct status
            mockMvc.perform(post("/admin/addCustomer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"email\":\"customer@example.com\",\"password\":\"password123\",\"customerName\":\"John Doe\"}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("customer email should be Unique"));
        }
    }
