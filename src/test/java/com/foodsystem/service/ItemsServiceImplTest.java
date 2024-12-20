package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.repo.IRestaurantRepo;
import com.foodsystem.service.impl.ItemsServiceImpl;
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

public class ItemsServiceImplTest {

    @Mock
    private IRestaurantRepo restaurantRepo;
    private Restaurant restaurant;
    private Items items, updatedItem;
    @Mock
    private IItemsRepo itemsRepo;
    List<Items> itemsList;
    @InjectMocks
    private ItemsServiceImpl itemsService;

    @Test
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant();
        restaurant.setRestaurantId(1);
        restaurant.setRestaurantName("Test Restaurant");

        items = new Items();
        items.setItemName("Old Item");
        items.setItemCost(100.0);
        items.setQuantity(10);
        items.setIsAvailable("Yes");
        items.setCategory("Beverage");

        updatedItem = new Items();
        updatedItem.setItemName("New Item");
        updatedItem.setItemCost(120.0);
        updatedItem.setQuantity(20);
        updatedItem.setIsAvailable("Yes");
        updatedItem.setCategory("Snack");
        itemsList = new ArrayList<>();
        itemsList.add(items);
        itemsList.add(updatedItem);

    }
    @Test
    public void testAddItems_Success() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
        when(itemsRepo.findByItemName("Test Item")).thenReturn(Optional.empty());
        ApiResponse response = itemsService.addItems(1, items);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testAddItems_ItemNameNotUnique() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.of(restaurant));
        when(itemsRepo.findByItemName("Old Item")).thenReturn(Optional.of(items));
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.addItems(1, items);
        });
    }

    @Test
    public void testAddItems_RestaurantNotFound() {
        when(restaurantRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.addItems(1, items);
        });
    }

    @Test
    public void testRemoveItems_Success() {
        when(itemsRepo.findByItemName("Test Item")).thenReturn(Optional.of(items));
        ApiResponse response = itemsService.removeItems("Test Item");
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testRemoveItems_ItemNotFound() {
        when(itemsRepo.findByItemName("Non-Existent Item")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () ->
        {
            itemsService.removeItems("Non-Existent Item");
        });
    }

    @Test
    public void testUpdateItems_Success() {
        when(itemsRepo.findByItemName("Old Item")).thenReturn(Optional.of(items));
        when(itemsRepo.findByItemName("New Item")).thenReturn(Optional.empty());
        ApiResponse response = itemsService.updateItems("Old Item", updatedItem);
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testUpdateItems_ItemNotFound() {
        when(itemsRepo.findByItemName("Non-Existent Item")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.updateItems("Non-Existent Item", updatedItem);
        });
    }

    @Test
    public void testUpdateItems_ItemNameConflict() {
        when(itemsRepo.findByItemName("Old Item")).thenReturn(Optional.of(items));

        when(itemsRepo.findByItemName("New Item")).thenReturn(Optional.of(new Items()));

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.updateItems("Old Item", updatedItem);
        });
    }

    @Test
    public void testIsItemAvailable_Success() {
        when(itemsRepo.findByItemName("New Item")).thenReturn(Optional.of(items));
        ApiResponse response = itemsService.IsItemAvailable("New Item", "No");
        assertNotNull(response);
        assertTrue(response.getSuccess());
    }

    @Test
    public void testIsItemAvailable_ItemNotFound() {
        when(itemsRepo.findByItemName("New Item")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.IsItemAvailable("New Item", "No");
        });

    }

    @Test
    public void testGetAllItems_Success() {
        when(itemsRepo.findAll()).thenReturn(itemsList);
        List<Items> result = itemsService.getAllItems();
        assertNotNull(result);
    }

    @Test
    public void testGetAllItems_EmptyList() {
        when(itemsRepo.findAll()).thenReturn(new ArrayList<>());
        List<Items> result = itemsService.getAllItems();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetItemByName_Success() {
        when(itemsRepo.findByItemName("Old Item")).thenReturn(Optional.of(items));
        Items result = itemsService.getItemByName("Old Item");
        assertNotNull(result);
    }

    @Test
    public void testGetItemByName_ItemNotFound() {
        when(itemsRepo.findByItemName("Old Item")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemsService.getItemByName("Old Item");
        });
    }


}
