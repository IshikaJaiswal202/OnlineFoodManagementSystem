package com.foodsystem.service.impl;

import com.foodsystem.entity.Admin;
import com.foodsystem.entity.Customer;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IAdminRepo;
import com.foodsystem.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXAuthenticator;
import java.util.Optional;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @Autowired
    IAdminRepo adminRepo;
    @Override
    public String verifyForLogin(Admin admin) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(admin.getEmail());
            }
        } catch (Exception e)
        {
            throw new ResourceNotFoundExceptions("Invalid email or password. Please try again.");
        }
        throw new ResourceNotFoundExceptions("Authentication failed. Please check your credentials.");
    }


}
