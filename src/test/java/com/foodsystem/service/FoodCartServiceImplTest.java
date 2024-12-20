package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.FoodCart;
import com.foodsystem.entity.Items;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.repo.IFoodCartRepo;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.service.impl.FoodCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class FoodCartServiceImplTest {
    @Mock
    private ICustomerRepo customerRepo;
    @Mock
    private IFoodCartRepo cartRepo;

    @Mock
    private IItemsRepo itemsRepo;
    @InjectMocks
    private FoodCartServiceImpl foodCartService;

    private Customer customer;
    private FoodCart foodCart;
    private Items item,item1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerName("ishika");
        item = new Items();
        item.setItemId(1);
        item.setItemName("Burger");
        item.setItemCost(70.00);
        item1 = new Items();
        item1.setItemId(101);
        item1.setItemName("Pizza");
        foodCart = new FoodCart();
        foodCart.setCartId(1);
        foodCart.setItems(new ArrayList<>());
    }

    @Test
    void testAddFoodCart_Success() {
        Integer customerId = 1;
        FoodCart foodCart = new FoodCart();
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(cartRepo.findByCustomerCustomerId(customerId)).thenReturn(Optional.empty());
        when(cartRepo.save(foodCart)).thenReturn(foodCart);
        ApiResponse response = foodCartService.addFoodCart(customerId, foodCart);

        assertNotNull(response);
        assertEquals("Successfully FoodCart is Created", response.getMsg());
      }

    @Test
    void testAddFoodCart_CustomerNotFound() {
        Integer customerId = 1;
        FoodCart foodCart = new FoodCart();

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> foodCartService.addFoodCart(customerId, foodCart));
    }


    @Test
    void testAddFoodCart_AlreadyHasFoodCart() {
        Integer customerId = 1;
        FoodCart existingFoodCart = new FoodCart();
        FoodCart newFoodCart = new FoodCart();
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(cartRepo.findByCustomerCustomerId(customerId)).thenReturn(Optional.of(existingFoodCart));
        assertThrows(ResourceNotFoundExceptions.class, () -> foodCartService.addFoodCart(customerId, newFoodCart));
       }

   @Test
    public void testAddFoodCart_FoodCartAlreadyExists() {
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(cartRepo.findById(1)).thenReturn(Optional.of(foodCart));
    }

    // *************
    @Test
    public void testSaveItemsToFoodCart_Success() {
        when(itemsRepo.findByRestaurantIdAndItemId(101, 1)).thenReturn(Optional.of(item));
        when(cartRepo.findById(1)).thenReturn(Optional.of(foodCart));
        when(cartRepo.save(any(FoodCart.class))).thenReturn(foodCart);
        List<Items> result = foodCartService.saveItemsToFoodCart(101, 1, 1);
        assertNotNull(result);
    }

    @Test
    public void testSaveItemsToFoodCart_ItemNotFound() {
        when(itemsRepo.findByRestaurantIdAndItemId(101, 1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            foodCartService.saveItemsToFoodCart(101, 1, 1);
        });
        verify(cartRepo, never()).save(any());
    }

    @Test
    public void testSaveItemsToFoodCart_CartNotFound() {
        when(itemsRepo.findByRestaurantIdAndItemId(101, 1)).thenReturn(Optional.of(item));
        when(cartRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> {
            foodCartService.saveItemsToFoodCart(101, 1, 1);
        });
        verify(cartRepo, never()).save(any());
    }

    void testGetAllItems_FoodCart_Success() {
        List<Items> itemsList = Arrays.asList(item, item1);
        FoodCart foodCart = new FoodCart();
        foodCart.setCartId(1);
        foodCart.setItems(itemsList);
        when(cartRepo.findByCustomerCustomerId(customer.getCustomerId())).thenReturn(Optional.of(foodCart));
        List<Items> result = foodCartService.getAllItems_FoodCart(customer.getCustomerId());
        assertNotNull(result);
    }

    @Test
    void testGetAllItems_FoodCart_CustomerNotFound() {
        when(cartRepo.findByCustomerCustomerId(customer.getCustomerId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class, () -> foodCartService.getAllItems_FoodCart(customer.getCustomerId()));
    }

    @Test
    void saveFoodCartTest(){
        foodCartService.saveFoodCart(null);
    }

}
