package com.foodsystem.controller;

import com.foodsystem.entity.Restaurant;
import com.foodsystem.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminTestController
{

    @Mock
    private RestaurantServiceImpl restaurantService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testGetAllRestaurant_Success() throws Exception {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantName("Restaurant 1");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantName("Restaurant 2");

        List<Restaurant> restaurantList = Arrays.asList(restaurant1, restaurant2);
        when(restaurantService.getAllRestaurant()).thenReturn(restaurantList);
        mockMvc.perform(get("/admin/getAllRestaurant"))
                .andExpect(status().isOk());
    }


}
