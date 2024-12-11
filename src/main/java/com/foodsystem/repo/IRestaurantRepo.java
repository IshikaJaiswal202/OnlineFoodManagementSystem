package com.foodsystem.repo;

import com.foodsystem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepo extends JpaRepository<Restaurant, Integer> {


    Optional<Restaurant> findByRestaurantName(String restaurantName);
}
