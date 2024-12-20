package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.repo.IRestaurantRepo;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RestaurantServiceImplTest {

    @Mock
    private IRestaurantRepo restaurantRepo;

    @Mock
    private IItemsRepo itemRepo;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant updatedRestaurant;

    private Restaurant restaurant, restaurant1;

    private Items item, item1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant();
        restaurant.setRestaurantName("The Good Eatery");
        restaurant.setDescription("xyz");
        restaurant.setStatus(true);
        ArrayList<Items> items = new ArrayList<>();
        item = new Items();
        item.setItemName("Pizza");
        item.setItemCost(45.00);
        items.add(item);
        restaurant.setItems(items);
        item1 = new Items();
        item1.setItemName("Pasta");
        item1.setItemCost(35.00);
        items.add(item1);

        restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Pizza Place");
        restaurant1.setDescription("Tasty pizzas");

        updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantName("The Better Eatery");
        updatedRestaurant.setDescription("Even more delicious food");
    }

    @Test
    public void testAddRestaurant_Success() {
        when(restaurantRepo.findByRestaurantName(anyString())).thenReturn(Optional.empty());
        ApiResponse response = restaurantService.addRestaurant(restaurant);

        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());
        verify(restaurantRepo, times(1)).save(restaurant);
    }

    @Test
    public void testAddRestaurant_NameAlreadyExists() {

        when(restaurantRepo.findByRestaurantName(anyString())).thenReturn(Optional.of(restaurant));
        assertThrows(ResourceNotFoundExceptions.class, () -> restaurantService.addRestaurant(restaurant));
        verify(restaurantRepo, never()).save(restaurant);
    }

    @Test
    public void testGetAllRestaurant() {
        when(restaurantRepo.findAll()).thenReturn(Arrays.asList(restaurant, restaurant1));
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        assertNotNull(restaurants);
        assertEquals(2, restaurants.size());
    }

    @Test
    public void testGetRestaurantById_Success() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
        when(itemRepo.findByRestaurantRestaurantId(1)).thenReturn(Arrays.asList(item, item1));
        List<Items> items = restaurantService.getRestaurantById(1);
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    public void testGetRestaurantById_RestaurantNotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> restaurantService.getRestaurantById(1));
    }


    @Test
    public void testGetRestaurantByName_Success() {
        when(restaurantRepo.findByRestaurantName("The Good Eatery")).thenReturn(java.util.Optional.of(restaurant));
        Restaurant result = restaurantService.getRestaurantByName("The Good Eatery");
        assertNotNull(result);
        assertEquals("The Good Eatery", result.getRestaurantName());
    }

    @Test
    public void testGetRestaurantByName_RestaurantNotFound() {
        when(restaurantRepo.findByRestaurantName("Nonexistent Restaurant")).
                thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () ->
                restaurantService.getRestaurantByName("Nonexistent Restaurant"));
    }


    @Test
    public void testUpdateRestaurantInfo_Success() {
        when(restaurantRepo.findByRestaurantName("The Good Eatery")).thenReturn(Optional.of(restaurant));
        when(restaurantRepo.findByRestaurantName("The Better Eatery")).thenReturn(Optional.empty());
        ApiResponse response = restaurantService.updateRestaurantInfo("The Good Eatery", updatedRestaurant);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
    }

    @Test
    public void testUpdateRestaurantInfo_RestaurantNotFound() {
        when(restaurantRepo.findByRestaurantName("NonExisting Restaurant")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            restaurantService.updateRestaurantInfo("NonExisting Restaurant", updatedRestaurant);
        });
    }

    @Test
    public void testUpdateRestaurantInfo_NameNull() {
        when(restaurantRepo.findByRestaurantName("The Good Eatery")).thenReturn(Optional.of(restaurant));
        updatedRestaurant.setRestaurantName(null);
        ApiResponse response = restaurantService.updateRestaurantInfo("The Good Eatery", updatedRestaurant);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());
    }

    @Test
    public void testSafeDeleteRestaurant_Success() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
        ApiResponse response = restaurantService.safeDeleteRestaurant(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());
    }

    @Test
    public void testSafeDeleteRestaurant_RestaurantNotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            restaurantService.safeDeleteRestaurant(1);
        });
    }

    @Test
    public void testDeleteRestaurantById_Success() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
        ApiResponse response = restaurantService.deleteRestaurantById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());
    }

    @Test
    public void testDeleteRestaurantById_RestaurantNotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            restaurantService.deleteRestaurantById(1);
        });
    }
}


