package com.foodsystem.service.impl;

import com.foodsystem.entity.Admin;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.principal.AdminPrincipal;
import com.foodsystem.repo.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    IAdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundExceptions("No User Found By This Email Please Check"));
         return new AdminPrincipal(admin);
    }
}
