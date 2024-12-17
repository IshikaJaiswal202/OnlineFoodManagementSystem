package com.foodsystem.service;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.repo.IRestaurantRepo;
import com.foodsystem.service.impl.CustomerServiceImpl;
import com.foodsystem.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest
{
    @Mock
    private ICustomerRepo repo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private IRestaurantRepo restaurantRepo;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Customer customer;

    private Restaurant restaurant;


    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setEmail("customer@example.com");
        customer.setCustomerName("John Doe");
        customer.setPassword("password123");
        customer.setStatus(true);

        restaurant=new Restaurant();
        restaurant.setRestaurantName("The Good Eatery");
        restaurant.setDescription("xyz");
        restaurant.setStatus(true);
        ArrayList<Items> items=new ArrayList<>();
        Items item=new Items();
        item.setItemName("Pizza");
        item.setItemCost(45.00);
        items.add(item);
    }

    @Test
    public void testSoftDeleteCustomer_Success() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(customer));

        ApiResponse response = customerService.softDeleteCustomer("customer@example.com");

        assertEquals("Successfully customer removed", response.getMsg());
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());

        verify(repo, times(1)).save(customer);

        assertFalse(customer.getStatus());
    }

    @Test
    public void testSoftDeleteCustomer_CustomerNotFound() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            customerService.softDeleteCustomer("nonexistent@example.com");
        });
    }


    @Test
    public void testPermanentDeleteCustomer_Success() {
        String email = "test@example.com";
        Customer mockCustomer = new Customer();
        mockCustomer.setEmail(email);


        when(repo.findByEmail(email)).thenReturn(Optional.of(mockCustomer));

        ApiResponse response = customerService.permanentDeleteCustomer(email);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getCode());
        assertTrue(response.getSuccess());

        verify(repo, times(1)).deleteByEmail(email);
    }
    @Test
    public void testPermanentDeleteCustomer_CustomerNotFound() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundExceptions.class,()-> customerService.permanentDeleteCustomer("test@example.com"));
        verify(repo,never()).deleteByEmail(anyString());
    }

    @Test
    public void testAddRestaurant_Success() {
        when(restaurantRepo.findByRestaurantName(anyString())).thenReturn(Optional.empty());
        ApiResponse response=restaurantService.addRestaurant(restaurant);

        assertEquals(HttpStatus.OK,response.getCode());
        assertTrue(response.getSuccess());
        verify(restaurantRepo,times(1)).save(restaurant);
    }

    @Test
    public void testAddRestaurant_NameAlreadyExists() {

        when(restaurantRepo.findByRestaurantName(anyString())).thenReturn(Optional.of(restaurant));
        assertThrows(ResourceNotFoundExceptions.class,()-> restaurantService.addRestaurant(restaurant));
        verify(restaurantRepo,never()).save(restaurant);
    }


    }
