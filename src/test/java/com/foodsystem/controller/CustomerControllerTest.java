package com.foodsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Orders;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.service.impl.CustomerServiceImpl;
import com.foodsystem.service.impl.FoodCartServiceImpl;
import com.foodsystem.service.impl.OrderServiceImpl;
import com.foodsystem.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    OrderServiceImpl orderService;

    @Mock
    CustomerServiceImpl customerService;

    @Mock
    RestaurantServiceImpl restaurantService;
    List<Items> items;
    @Mock
    FoodCartServiceImpl cartService;
    private Customer customer;
    private Items item1, item2;
    private ObjectMapper objectMapper;
    private Restaurant restaurant1, restaurant2;
    private Orders order;
    private ApiResponse response;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setPassword("password123");
        objectMapper = new ObjectMapper();
        restaurant1 = new Restaurant();
        restaurant1.setRestaurantId(1);
        restaurant1.setRestaurantName("Restaurant A");
        restaurant1.setDescription("xyz");
        restaurant2 = new Restaurant();
        restaurant2.setRestaurantId(2);
        restaurant2.setRestaurantName("Restaurant B");
        restaurant2.setDescription("abc");

        item1 = new Items();
        item1.setItemId(1);
        item1.setItemName("Burger");
        item1.setItemCost(5.99);
        item1.setQuantity(1);
        item1.setIsAvailable("Yes");
        item1.setCategory("Fast Food");

        item2 = new Items();
        item2.setItemId(2);
        item2.setItemName("Fries");
        item2.setItemCost(2.99);
        item2.setQuantity(1);
        item2.setIsAvailable("Yes");
        item2.setCategory("Fast Food");
        items = Arrays.asList(item1, item2);

        order = new Orders();
        order.setOrderId(1);
        order.setTotalAmount(100.0);

        response = new ApiResponse.Builder().msg("Order placed successfully").code(HttpStatus.OK)
                .success(true).build();

    }

    @Test
    public void testLoginCustomer_Success() {
        when(customerService.verifyForLogin(customer)).thenReturn(response);
        ResponseEntity<ApiResponse> response = customerController.loginCustomer(customer);
        assertNotNull(response);
    }

    @Test
    public void testGetRestaurantByName_Success() {
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setRestaurantName("Pizza Hut");
        mockRestaurant.setRestaurantId(1);
        when(restaurantService.getRestaurantByName("Pizza Hut")).thenReturn(mockRestaurant);
        ResponseEntity<Restaurant> responseEntity = customerController.getRestaurantByName("Pizza Hut");
        assertNotNull(responseEntity);
    }

    @Test
    public void testGetAllRestaurant() {
        List<Restaurant> restaurantList = Arrays.asList(restaurant1, restaurant2);
        when(restaurantService.getAllRestaurant()).thenReturn(restaurantList);
        ResponseEntity<List<Restaurant>> response = customerController.getAllRestaurant();
        assertNotNull(response);
    }

    @Test
    public void testSaveItemsToFoodCart() {

        when(cartService.saveItemsToFoodCart(1, 1, 1)).thenReturn(items);
        ResponseEntity<List<Items>> response = customerController.saveItemsToFoodCart(1, 1, 1);
        assertNotNull(response);
    }

    @Test
    public void testGetAllItems_FoodCart_Success() {
        when(cartService.getAllItems_FoodCart(1)).thenReturn(items);
        customerController.getRestaurantByName(restaurant1.getRestaurantName());
        assertNotNull(response);
    }

    @Test
    public void testPalaceOrder_Success() {
        when(orderService.palaceOrder(1, order)).thenReturn(response);
        ResponseEntity<ApiResponse> response = customerController.palaceOrder(1, order);
        assertNotNull(response);
    }

}
