package com.foodsystem.repo;

import com.foodsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAdminRepo extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByPassword(String password);
}
