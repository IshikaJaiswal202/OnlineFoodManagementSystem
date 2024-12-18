package com.foodsystem.service.impl;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    JwtService jwtService;
    @Autowired
    ICustomerRepo repo;

    @Autowired
    AuthenticationManager authenticationManager;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public ApiResponse addCustomer(Customer customer) {
        Optional<Customer> customers = repo.findByEmail(customer.getEmail());
        if (customers.isEmpty()) {
            customer.setPassword(encoder.encode(customer.getPassword()));
            System.out.println(customer);
            repo.save(customer);
        } else
            throw new ResourceNotFoundExceptions(" customer email should be Unique");

        return new ApiResponse.Builder().
                msg("Successfully added customer ").
                code(HttpStatus.CREATED).
                success(true).
                build();
    }

    @Override
    public ApiResponse softDeleteCustomer(String email) {
        Customer customer = repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundExceptions("No customer found By this email"));
        customer.setStatus(false);
        repo.save(customer);
        return new ApiResponse.Builder().
                msg("Successfully customer removed").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse permanentDeleteCustomer(String email) {
        repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundExceptions("No customer found By this email"));
        repo.deleteByEmail(email);
        return new ApiResponse.Builder().
                msg("Successfully Permanent Deleted").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public String verifyForLogin(Customer customer) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPassword()));
            if (authentication.isAuthenticated()) {
                System.out.println(customer.getEmail());
                return jwtService.generateToken(customer.getEmail());
            }
        }
        catch (BadCredentialsException e)
        {
            throw new ResourceNotFoundExceptions("Failed To LogIn");
        }

        throw new ResourceNotFoundExceptions("Failed To LogIn");
    }


}

