package com.foodsystem.repo;

import com.foodsystem.entity.FoodCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFoodCartRepo extends JpaRepository<FoodCart ,Integer> {
}
