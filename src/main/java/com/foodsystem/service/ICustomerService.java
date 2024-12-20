package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;

public interface ICustomerService {
     public ApiResponse addCustomer(Customer customer);

     ApiResponse softDeleteCustomer(String email);

     ApiResponse permanentDeleteCustomer(String email);

     ApiResponse verifyForLogin(Customer customer);
}
