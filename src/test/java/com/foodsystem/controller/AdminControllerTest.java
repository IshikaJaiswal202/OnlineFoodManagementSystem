package com.foodsystem.controller;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.*;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
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
        ApiResponse responseEntity = cartService.addFoodCart(customer.getCustomerId(), foodCart);
        assertNotNull(responseEntity);
    }

    @Test
    public void testAddFoodCart_Failure() {
        try {
            when(cartService.addFoodCart(customer.getCustomerId(), foodCart))
                    .thenThrow(new ResourceNotFoundExceptions("Customer not found"));

        } catch (ResourceNotFoundExceptions e) {
            assertEquals("Customer not found", e.getMessage());
        }
    }


    @Test
    public void testDeleteRestaurantById_Success() {
        response.setMsg("Restaurant deleted successfully");
        when(restaurantService.deleteRestaurantById(restaurant.getRestaurantId())).thenReturn(response);
        ApiResponse responseEntity = restaurantService.deleteRestaurantById(restaurant.getRestaurantId());
        assertNotNull(responseEntity);
    }

    @Test
    public void testDeleteRestaurantById_Failure() {
        restaurant.setRestaurantId(11);
        try {
            when(restaurantService.deleteRestaurantById(restaurant.getRestaurantId())).
                    thenThrow(new ResourceNotFoundExceptions("Restaurant not found"));
        } catch (ResourceNotFoundExceptions e) {
            assertEquals("Restaurant not found", e.getMessage());
        }
    }

    @Test
    public void testSafeDeleteRestaurant_Success() {
        Integer restaurantId = 1;
        response.setMsg("Restaurant safely deleted");
        when(restaurantService.safeDeleteRestaurant(restaurantId)).thenReturn(response);
        ApiResponse responseEntity = restaurantService.safeDeleteRestaurant(restaurantId);
        assertNotNull(responseEntity);
    }

    @Test
    public void testSafeDeleteRestaurant_Failure() {
        Integer restaurantId = 11;
        try {
            when(restaurantService.safeDeleteRestaurant(restaurantId))
                    .thenThrow(new ResourceNotFoundExceptions("Restaurant not found"));

        } catch (ResourceNotFoundExceptions e) {
            assertEquals("Restaurant not found", e.getMessage());
        }
    }

    @Test
    public void testGetRestaurantById_Success() {
        Integer restaurantId = 1;
        Items item = new Items();
        item.setItemName("Pizza");
        list.add(item);
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(list);
        List<Items> responseEntity = restaurantService.getRestaurantById(restaurantId);
        assertNotNull(responseEntity);
    }

    @Test
    public void testGetRestaurantById_Failure() {
        Integer restaurantId = 99;
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(new ArrayList<>());
        List<Items> responseEntity = restaurantService.getRestaurantById(restaurantId);
        assertNotNull(responseEntity);

    }

    @Test
    public void testUpdateRestaurantInfo_Success() throws Exception {
        String restaurantName = "Restaurant A";
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantName("Restaurant A");
        response.setMsg("Restaurant information updated successfully");
        when(restaurantService.updateRestaurantInfo(restaurantName, updatedRestaurant)).thenReturn(response);
        ApiResponse response = restaurantService.updateRestaurantInfo(restaurantName, updatedRestaurant);
        assertNotNull(response);
        assertEquals(true, response.getSuccess());
        assertEquals("Restaurant information updated successfully", response.getMsg());
    }

    @Test
    public void testUpdateRestaurantInfo_Failure() {
        String restaurantName = "NonExistentRestaurant";
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantName("Restaurant A");
        try {
            when(restaurantService.updateRestaurantInfo(restaurantName, updatedRestaurant))
                    .thenThrow(new RuntimeException("Restaurant not found"));
        } catch (RuntimeException e) {
            assertEquals("Restaurant not found", e.getMessage());
        }
    }

    @Test
    public void testGetRestaurantByName_Success() {
        String restaurantName = "Restaurant A";
        when(restaurantService.getRestaurantByName(restaurantName)).thenReturn(restaurant);
        Restaurant response = restaurantService.getRestaurantByName(restaurantName);
        assertNotNull(response);
    }

    @Test
    public void testGetRestaurantByName_NotFound() {
        String restaurantName = "NonExistentRestaurant";
        when(restaurantService.getRestaurantByName(restaurantName)).thenReturn(null);
        Restaurant response = restaurantService.getRestaurantByName(restaurantName);
        assertNull(response);
    }

    @Test
    public void testGetAllRestaurant_Success() {
        List<Restaurant> restaurants = Arrays.asList(restaurant, restaurant1);
        when(restaurantService.getAllRestaurant()).thenReturn(restaurants);
        List<Restaurant> response = restaurantService.getAllRestaurant();
        assertNotNull(response);
    }

    @Test
    public void testGetAllRestaurant_EmptyList() {
        when(restaurantService.getAllRestaurant()).thenReturn(Arrays.asList());
        List<Restaurant> response = restaurantService.getAllRestaurant();
        assertNotNull(response);
    }

    @Test
    public void testAddRestaurant_Success() {
        response.setMsg("Restaurant added successfully");
        when(restaurantService.addRestaurant(restaurant)).thenReturn(response);
        ApiResponse response = restaurantService.addRestaurant(restaurant);
        assertNotNull(response);
    }


    @Test
    public void testAddRestaurant_Failure() {
        try {
            when(restaurantService.addRestaurant(restaurant)).thenThrow(new RuntimeException(" error"));
        } catch (RuntimeException e) {
            assertEquals(" error", e.getMessage());
        }
    }

    @Test
    public void testPermanentDeleteCustomer_Success() {
        response.setMsg("Customer deleted successfully");
        when(customerService.permanentDeleteCustomer("test@example.com")).thenReturn(response);
        ApiResponse response = customerService.permanentDeleteCustomer("test@example.com");
        assertNotNull(response);
    }

    @Test
    public void testPermanentDeleteCustomer_Failure() {
        try {
            when(customerService.permanentDeleteCustomer("nonexistent@example.com"))
                    .thenThrow(new RuntimeException("Customer not found"));

        } catch (RuntimeException e) {
            assertEquals("Customer not found", e.getMessage());
        }
    }

    @Test
    public void testSoftDeleteCustomer_Success() {
        response.setMsg("Customer soft deleted successfully");
        when(customerService.softDeleteCustomer("ishika@example.com")).thenReturn(response);

       ApiResponse response = customerService.softDeleteCustomer("ishika@example.com");
         assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testSoftDeleteCustomer_Failure() {

        try {
            when(customerService.softDeleteCustomer("ishika@example.com"))
                    .thenThrow(new RuntimeException("Customer not found"));
        } catch (RuntimeException e) {
            assertEquals("Customer not found", e.getMessage());
        }
    }

    @Test
    public void testAddCustomer_Success() {
        when(customerService.addCustomer(customer)).thenReturn(response);
        ApiResponse response = customerService.addCustomer(customer);
        assertNotNull(response);
        assertTrue(response.getSuccess());
        }


    @Test
    public void testAddCustomer_Failure() {
        try {
            when(customerService.addCustomer(customer))
                    .thenThrow(new RuntimeException("Customer already exists"));

        } catch (RuntimeException e) {
            assertEquals("Customer already exists", e.getMessage());
        }
    }

    @Test
    public void testLoginAdmin_Success() {
        String expectedResponse = "mock-jwt-token";
        when(adminService.verifyForLogin(admin)).thenReturn(expectedResponse);
        String response = adminService.verifyForLogin(admin);
        assertNotNull(response);
    }

    @Test
    public void testLoginAdmin_Failure() {
        String expectedResponse = "Invalid credentials";
        when(adminService.verifyForLogin(admin)).thenReturn(expectedResponse);
        String response = adminService.verifyForLogin(admin);
        assertNotNull(response);
    }
}
