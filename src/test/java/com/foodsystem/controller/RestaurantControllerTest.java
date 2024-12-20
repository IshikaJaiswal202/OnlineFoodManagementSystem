package com.foodsystem.controller;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.service.impl.ItemsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class RestaurantControllerTest {

    @Mock
    ItemsServiceImpl itemsService;
    @InjectMocks
    RestaurantController restaurantController;
    List<Items> item;
    private Items items, item1, updatedItem;
    private ApiResponse response;

    @BeforeEach
    public void setUp() {
        org.mockito.MockitoAnnotations.initMocks(this);
        item = new ArrayList<>();
        items = new Items();
        items.setItemId(1);
        items.setItemName("Fries");
        items.setItemCost(2.99);
        items.setQuantity(1);
        items.setIsAvailable("Yes");
        items.setCategory("Fast Food");
        item1 = new Items();
        item1.setItemId(2);
        item1.setItemName("Pizza");
        item1.setItemCost(2.99);
        item1.setQuantity(1);
        item1.setIsAvailable("Yes");
        item1.setCategory("Fast Food");
        item.add(items);
        item.add(item1);
        updatedItem = new Items();
        updatedItem.setItemName("Bugger");
        updatedItem.setItemCost(153.99);
        updatedItem.setIsAvailable("Yes");
        response = new ApiResponse.Builder().msg("Item availability updated successfully").code(HttpStatus.OK)
                .success(true).success(true).build();
    }

    @Test
    public void testGetItemByName_Success() {
        when(itemsService.getItemByName("Pizza")).thenReturn(items);
        ResponseEntity<Items> response = restaurantController.getItemByName("Pizza");
        assertNotNull(this.response);

    }

    @Test
    public void testGetAllItems_Success() {
        when(itemsService.getAllItems()).thenReturn(item);
        ResponseEntity<List<Items>> response = restaurantController.getAllItems();
        assertNotNull(response);
    }

    @Test
    public void testIsItemAvailable_Success() {
        when(itemsService.IsItemAvailable("Pizza", "true")).thenReturn(response);
        ResponseEntity<ApiResponse> response = restaurantController.IsItemAvailable("Pizza", "true");
        assertNotNull(response);
    }

    @Test
    public void testUpdateItems_Success() {
        response.setMsg("Item updated successfully");
        when(itemsService.updateItems("Pizza", updatedItem)).thenReturn(response);
        ResponseEntity<ApiResponse> response = restaurantController.updateItems("Pizza", updatedItem);
        assertNotNull(response);
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    public void testDeleteItem_Success() {
        response.setMsg("Item deleted successfully");
        when(itemsService.removeItems(items.getItemName())).thenReturn(response);
        ResponseEntity<ApiResponse> responseEntity = restaurantController.deleteItem(items.getItemName());
        assertNotNull(responseEntity);
        ;
    }

    @Test
    public void testAddItems_Success() {
        response.setMsg("Item added successfully");
        when(itemsService.addItems(items.getItemId(), items)).thenReturn(response);
        ResponseEntity<ApiResponse> response = restaurantController.addItems(items.getItemId(), items);
        assertNotNull(response);
    }

}
