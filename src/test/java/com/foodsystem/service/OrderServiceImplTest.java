package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.FoodCart;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Orders;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IFoodCartRepo;
import com.foodsystem.repo.IOrderRepo;
import com.foodsystem.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    @Mock
    private IOrderRepo orderRepo;

    @Mock
    IFoodCartRepo cartRepo;

    private Orders order;
    @InjectMocks
    private OrderServiceImpl orderService;

    private FoodCart cart;
    Integer customerId;
    List<Items> cartItems;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = 1;
        cart = new FoodCart();
        Customer customer = new Customer();
        customer.setCustomerId(1);
        cart.setCustomer(customer);

        Items item1 = new Items();
        item1.setItemName("Pizza");
        item1.setItemCost(12.99);
        item1.setQuantity(2);

        Items item2 = new Items();
        item2.setItemName("Burger");
        item2.setItemCost(5.99);
        item2.setQuantity(1);

        cartItems = new ArrayList<>();
        cartItems.add(item1);
        cartItems.add(item2);

        cart.setItems(cartItems);

        order = new Orders();
        order.setItem(cartItems);
        order.setCustomer(customer);
    }

    @Test
    public void testPalaceOrder_Success() {
        when(cartRepo.findById(customerId)).thenReturn(Optional.of(cart));
        when(orderRepo.save(order)).thenReturn(order);

        ApiResponse response = orderService.palaceOrder(customerId, order);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testPalaceOrder_CustomerNotFound() {
        when(cartRepo.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            orderService.palaceOrder(customerId, new Orders());
        });

    }
}
