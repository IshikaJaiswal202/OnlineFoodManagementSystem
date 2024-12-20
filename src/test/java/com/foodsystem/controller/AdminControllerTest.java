package com.foodsystem.controller;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.*;
import com.foodsystem.service.impl.AdminServiceImpl;
import com.foodsystem.service.impl.CustomerServiceImpl;
import com.foodsystem.service.impl.FoodCartServiceImpl;
import com.foodsystem.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AdminControllerTest
{

    @Mock
    private FoodCartServiceImpl cartService;
    @Mock
    private RestaurantServiceImpl restaurantService;

    @Mock
    private CustomerServiceImpl customerService;
    List<Items> list;
    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminServiceImpl adminService;

    private Admin admin;
    private Restaurant restaurant, restaurant1;
    private ApiResponse response;
    private FoodCart foodCart;
    private Customer customer;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Restaurant B");
        restaurant1.setRestaurantId(2);
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("ishika");
        foodCart = new FoodCart();
        foodCart.setCartId(1);
        foodCart.setCustomer(customer);
        list = new ArrayList<>();
        response = new ApiResponse.Builder().msg("Food cart added successfully").code(HttpStatus.OK)
                .success(true).success(true).build();
    }


    @Test
    public void testAddFoodCart_Success() {
        when(cartService.addFoodCart(customer.getCustomerId(), foodCart)).thenReturn(response);
        ResponseEntity<ApiResponse> responseEntity = adminController.addFoodCart(customer.getCustomerId(), foodCart);
        assertNotNull(responseEntity);
    }

    @Test
    public void testDeleteRestaurantById_Success() {
        response.setMsg("Restaurant deleted successfully");
        when(restaurantService.deleteRestaurantById(restaurant.getRestaurantId())).thenReturn(response);
        ResponseEntity<ApiResponse> responseEntity = adminController.deleteRestaurantById(restaurant.getRestaurantId());
        assertNotNull(responseEntity);
    }

    @Test
    public void testSafeDeleteRestaurant_Success() {
        Integer restaurantId = 1;
        response.setMsg("Restaurant safely deleted");
        when(restaurantService.safeDeleteRestaurant(restaurantId)).thenReturn(response);
        ResponseEntity<ApiResponse> responseEntity = adminController.safeDeleteRestaurant(restaurantId);
        assertNotNull(responseEntity);
    }

    @Test
    public void testGetRestaurantById_Success() {
        Integer restaurantId = 1;
        Items item = new Items();
        item.setItemName("Pizza");
        list.add(item);
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(list);
        List<Items> list = restaurantService.getRestaurantById(restaurantId);
        assertNotNull(list);
    }

    @Test
    public void testUpdateRestaurantInfo_Success() throws Exception {
        String restaurantName = "Restaurant A";
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantName("Restaurant A");
        response.setMsg("Restaurant information updated successfully");
        when(restaurantService.updateRestaurantInfo(restaurantName, updatedRestaurant)).thenReturn(response);
        ResponseEntity<ApiResponse> response = adminController.updateRestaurantInfo(restaurantName, updatedRestaurant);
        assertNotNull(response);
        assertEquals(true, response.getBody().getSuccess());
        assertEquals("Restaurant information updated successfully", response.getBody().getMsg());
    }

    @Test
    public void testGetRestaurantByName_Success() {
        String restaurantName = "Restaurant A";
        when(restaurantService.getRestaurantByName(restaurantName)).thenReturn(restaurant);
        ResponseEntity<Restaurant> response = adminController.getRestaurantByName(restaurantName);
        assertNotNull(response);
    }

    @Test
    public void testGetAllRestaurant_Success() {
        List<Restaurant> restaurants = Arrays.asList(restaurant, restaurant1);
        when(restaurantService.getAllRestaurant()).thenReturn(restaurants);
        ResponseEntity<List<Restaurant>> response = adminController.getAllRestaurant();
        assertNotNull(response);
    }

    @Test
    public void testGetAllRestaurant_EmptyList() {
        when(restaurantService.getAllRestaurant()).thenReturn(Arrays.asList());
        ResponseEntity<List<Restaurant>> response = adminController.getAllRestaurant();
        assertNotNull(response);
    }

    @Test
    public void testAddRestaurant_Success() {
        response.setMsg("Restaurant added successfully");
        when(restaurantService.addRestaurant(restaurant)).thenReturn(response);
        ResponseEntity<ApiResponse> response = adminController.addRestaurant(restaurant);
        assertNotNull(response);
    }

    @Test
    public void testPermanentDeleteCustomer_Success() {
        response.setMsg("Customer deleted successfully");
        when(customerService.permanentDeleteCustomer("test@example.com")).thenReturn(response);
        ResponseEntity<ApiResponse> response = adminController.permanentDeleteCustomer("test@example.com");
        assertNotNull(response);
    }

    @Test
    public void testSoftDeleteCustomer_Success() {
        response.setMsg("Customer soft deleted successfully");
        when(customerService.softDeleteCustomer("ishika@example.com")).thenReturn(response);

        ResponseEntity<ApiResponse> response = adminController.softDeleteCustomer("ishika@example.com");
         assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    public void testAddCustomer_Success() {
        when(customerService.addCustomer(customer)).thenReturn(response);
        ResponseEntity<ApiResponse> response = adminController.addCustomer(customer);
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
        }

    @Test
    public void testLoginAdmin_Success() {
        String expectedResponse = "mock-jwt-token";
        when(adminService.verifyForLogin(admin)).thenReturn(expectedResponse);
        ResponseEntity<String> response = adminController.loginAdmin(admin);
        assertNotNull(response);
    }
}
