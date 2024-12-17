package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;

import java.util.List;

public interface IRestaurantService {
    public ApiResponse addRestaurant(Restaurant restaurant) ;

    List<Restaurant> getAllRestaurant();

    Restaurant getRestaurantByName(String restaurantName);

    ApiResponse updateRestaurantInfo(String restaurantName,Restaurant restaurant);

    List<Items> getRestaurantById(Integer id);

    ApiResponse safeDeleteRestaurant(Integer id);

    ApiResponse deleteRestaurantById(Integer id);
}
