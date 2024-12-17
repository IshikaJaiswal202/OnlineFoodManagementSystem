package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;

import java.util.List;

public interface IItemsService {
    ApiResponse addItems(Integer id,Items item);

    ApiResponse removeItems(String itemName);

    ApiResponse updateItems(String itemName, Items items);

    ApiResponse IsItemAvailable(String itemName,String isAvailable);

    List<Items> getAllItems();

    Items getItemByName(String itemName);
}
