package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.FoodCart;
import com.foodsystem.entity.Items;

import java.util.List;

public interface IFoodCartService {
    ApiResponse saveFoodCart(Integer customerId);

    ApiResponse addFoodCart(Integer customerId, FoodCart foodCart);

    List<Items> saveItemsToFoodCart(Integer restaurantId, Integer itemId,Integer customerId);

    List<Items> getRestaurantByName(Integer customerId);
}
