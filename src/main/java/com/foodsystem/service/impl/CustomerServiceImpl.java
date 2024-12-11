package com.foodsystem.service.impl;

import com.foodsystem.entity.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    ICustomerRepo repo;
    @Override
    public ApiResponse addCustomer(Customer customer) {
        Optional<Customer> customers = repo.findByEmail(customer.getEmail());
        if(customers.isEmpty())
         repo.save(customer);
         else
          throw new ResourceNotFoundExceptions(" customer email should be Unique");

         return  new ApiResponse.Builder().
                msg("Successfully customer Added").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse softDeleteCustomer(String email) {
          Customer customer=repo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundExceptions("No customer found By this email"));
          customer.setStatus(false);
          repo.save(customer);
         return  new ApiResponse.Builder().
                 msg("Successfully customer removed").
                 code(HttpStatus.OK).
                 success(true).
                 build();
    }

    @Override
    public ApiResponse permanentDeleteCustomer(String email) {
        repo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundExceptions("No customer found By this email"));
        repo.deleteByEmail(email);
        return  new ApiResponse.Builder().
                msg("Successfully Permanent Deleted").
                code(HttpStatus.OK).
                success(true).
                build();
    }
}
