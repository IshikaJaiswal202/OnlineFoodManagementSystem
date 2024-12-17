package com.foodsystem.service.impl;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.*;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IFoodCartRepo;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.repo.IOrderRepo;
import com.foodsystem.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    IFoodCartRepo cartRepo;

    @Autowired
    IOrderRepo orderRepo;

    @Autowired
    IItemsRepo itemsRepo;

    @Override
    public ApiResponse palaceOrder(Integer customerId, Orders orders)
    {
        FoodCart cart = cartRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundExceptions("No Customer Found"));
        List<Items> itemsCopy = new ArrayList<>(cart.getItems());
        orders.setItem(itemsCopy);
        orders.setCustomer(cart.getCustomer());
        orderRepo.save(orders);
        return new ApiResponse.Builder().
                msg("Order Successfully Placed").
                code(HttpStatus.CREATED).
                success(true).
                build();
    }
}
