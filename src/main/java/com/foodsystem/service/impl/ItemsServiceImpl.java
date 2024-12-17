package com.foodsystem.service.impl;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.repo.IRestaurantRepo;
import com.foodsystem.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemsServiceImpl implements IItemsService {
    @Autowired
    IItemsRepo repo;

    @Autowired
    IRestaurantRepo restaurantRepo;
    @Override
    public ApiResponse addItems(Integer id,Items item) {
        Restaurant restaurant= restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant Found By this Id"));
        Optional<Items> items = repo.findByItemName(item.getItemName());
        if(items.isEmpty()) {
            item.setRestaurant(restaurant);
            repo.save(item);
        }
        else
           throw new ResourceNotFoundExceptions("Failed to Add(Item Name Should be Unique)");
        return  new ApiResponse.Builder().
                msg("Successfully item Added").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse removeItems(String itemName) {
        repo.findByItemName(itemName).orElseThrow(()-> new ResourceNotFoundExceptions("No Item Found BY This Name"));
        repo.deleteByItemName(itemName);
        return  new ApiResponse.Builder().
                msg("Successfully item Deleted").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse updateItems(String itemName, Items items)
    {
      Items item=  repo.findByItemName(itemName).orElseThrow(()-> new ResourceNotFoundExceptions("No Item Found BY This Name"));
      if(items.getItemName()!=null)
      {
          repo.findByItemName(items.getItemName()).orElseThrow(()-> new ResourceNotFoundExceptions("No Item Found BY This Name"));
          item.setItemName(items.getItemName());
      }
      item.setItemCost(items.getItemCost()==null? item.getItemCost() : items.getItemCost());
      item.setQuantity(items.getItemId()==null? item.getQuantity() : items.getQuantity());
      item.setCategory(items.getCategory()==null?item.getCategory(): items.getCategory());
      item.setIsAvailable(items.getIsAvailable()==null?item.getIsAvailable():items.getIsAvailable());
      item.setCategory(items.getCategory()==null?item.getCategory():items.getCategory());
      repo.save(item);
        return  new ApiResponse.Builder().
                msg("Successfully item Updated").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse IsItemAvailable(String itemName,String isAvailable) {
        Items item=  repo.findByItemName(itemName).orElseThrow(()-> new ResourceNotFoundExceptions("No Item Found BY This Name"));
        item.setIsAvailable(isAvailable);
        repo.save(item);
        return  new ApiResponse.Builder().
                msg("In Item changes Made Successfully").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public List<Items> getAllItems() {
        List<Items> list = repo.findAll();
        return list;
    }

    @Override
    public Items getItemByName(String itemName) {
        Items item=  repo.findByItemName(itemName).orElseThrow(()-> new ResourceNotFoundExceptions("No Item Found BY This Name"));
        return item;
    }


}
