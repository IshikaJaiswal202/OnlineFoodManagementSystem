package com.foodsystem.repo;

import com.foodsystem.entity.FoodCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFoodCartRepo extends JpaRepository<FoodCart ,Integer> {

    Optional<FoodCart> findByCustomerCustomerId(Integer customerId);
}
