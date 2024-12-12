package com.foodsystem.repo;

import com.foodsystem.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepo extends JpaRepository<Admin,Integer> {
}
