package com.foodsystem.service;

import com.foodsystem.entity.ApiResponse;
import com.foodsystem.entity.Orders;

public interface IOrderService {
    ApiResponse palaceOrder(Integer customerId, Orders orders);
}
