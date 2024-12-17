package com.foodsystem.service.impl;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.repo.IRestaurantRepo;
import com.foodsystem.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements IRestaurantService {
    @Autowired
    IRestaurantRepo repo;

    @Autowired
    IItemsRepo itemRepo;

    @Override
    public ApiResponse addRestaurant(Restaurant restaurant) {
        Optional<Restaurant> rest = repo.findByRestaurantName(restaurant.getRestaurantName());
        if (rest.isEmpty())
            repo.save(restaurant);
        else
            throw new ResourceNotFoundExceptions("Restaurant Name Should Be Unique");

        return new ApiResponse.Builder().
                msg("Successfully Restaurant Added").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        List<Restaurant> list = repo.findAll();
        return list;
    }

    @Override
    public Restaurant getRestaurantByName(String restaurantName) {
        Restaurant rest = repo.findByRestaurantName(restaurantName)
                .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this Name"));
        return rest;
    }

    @Override
    public ApiResponse updateRestaurantInfo(String restaurantName, Restaurant restaurant) {
        Restaurant rest = repo.findByRestaurantName(restaurantName)
                .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this Name"));
        if (restaurant.getRestaurantName() != null) {
            repo.findByRestaurantName(restaurantName)
                    .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this Name"));
            rest.setRestaurantName(restaurant.getRestaurantName());
        }
        return new ApiResponse.Builder().
                msg("Successfully Restaurant Detail Updated").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public List<Items>  getRestaurantById(Integer id) {
        Restaurant rest = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this Id"));
        return itemRepo.findByRestaurantRestaurantId(id);
    }

    @Override
    public ApiResponse safeDeleteRestaurant(Integer id) {
        Restaurant rest = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this id"));
        rest.setStatus(false);
        repo.save(rest);

        return new ApiResponse.Builder().
                msg("Successfully Delete Restaurant").
                code(HttpStatus.OK).
                success(true).
                build();
    }

    @Override
    public ApiResponse deleteRestaurantById(Integer id) {
         repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("No Restaurant found By this id"));
        repo.deleteById(id);
        return new ApiResponse.Builder().
                msg("Successfully Delete Permanent Restaurant").
                code(HttpStatus.OK).
                success(true).
                build();
    }
}
