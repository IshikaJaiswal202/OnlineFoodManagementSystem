package com.foodsystem.service;

import com.foodsystem.entity.ApiResponse;
import com.foodsystem.entity.Customer;

public interface ICustomerService {
     public ApiResponse addCustomer(Customer customer);

     ApiResponse softDeleteCustomer(String email);

     ApiResponse permanentDeleteCustomer(String email);

}
