package com.foodsystem.controller;

import com.foodsystem.entity.*;
import com.foodsystem.service.ICustomerService;
import com.foodsystem.service.IFoodCartService;
import com.foodsystem.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    ICustomerService customerService;


    @Autowired
    IFoodCartService cartService;
    // add the customer

    @Autowired
    IRestaurantService restaurantService;
    //customer Operations
    @PostMapping("/addCustomer")
    public ResponseEntity<ApiResponse> addCustomer(@RequestBody Customer customer)
    {
        ApiResponse response=customerService.addCustomer(customer);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
    }

    // Soft Delete the customer from the database
    @PutMapping("/softDeleteCustomer/{email}")
    public  ResponseEntity<ApiResponse> softDeleteCustomer(@PathVariable String email)
    {
        ApiResponse response= customerService.softDeleteCustomer(email);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/DeleteCustomer/{email}")
    public ResponseEntity<ApiResponse> permanentDeleteCustomer(@PathVariable String email)
    {
        ApiResponse response= customerService.permanentDeleteCustomer(email);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    // Restaurant Operation
     //addNewRestaurant
    @PostMapping("/addRestaurant")
    public ResponseEntity<ApiResponse> addRestaurant(@RequestBody Restaurant restaurant)
    {
        ApiResponse response=restaurantService.addRestaurant(restaurant);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
    }

    //getAllRestaurant
    @GetMapping("/getAllRestaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurant()
    {
        List<Restaurant> list=restaurantService.getAllRestaurant();
        return new ResponseEntity<List<Restaurant>>(list,HttpStatus.OK);
    }

   // getRestaurantByName
    @GetMapping("/getRestaurantByName/{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String restaurantName)
    {
        Restaurant restaurant=restaurantService.getRestaurantByName(restaurantName);
        return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
    }

    //UpdateRestaurant
    @PutMapping("/updateRestaurantInfo/{restaurantName}")
    public ResponseEntity<ApiResponse> updateRestaurantInfo(@PathVariable String restaurantName,@RequestBody Restaurant restaurant)
    {
        ApiResponse response=restaurantService.updateRestaurantInfo(restaurantName,restaurant);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    //add getRestaurantById
    @GetMapping("/getRestaurantById/{id}")
    public ResponseEntity<List<Items>> getRestaurantById(@PathVariable Integer id)
    {
        List<Items> itemsList=restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(itemsList,HttpStatus.OK);
    }

    //SafeDeleteRestaurant
    @PutMapping("/safeDeleteRestaurant/{id}")
    public  ResponseEntity<ApiResponse> safeDeleteRestaurant(@PathVariable Integer id)
    {
        ApiResponse response=restaurantService.safeDeleteRestaurant(id);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    //deleteFromDatabase
    @DeleteMapping("/deleteRestaurant/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurantById(@PathVariable Integer id)
    {
        ApiResponse response=restaurantService.deleteRestaurantById(id);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    //add FoodCart
    @PostMapping("/addFoodCart/{customerId}")
    public ResponseEntity<ApiResponse> addFoodCart(@PathVariable Integer customerId, @RequestBody FoodCart foodCart)
    {
        ApiResponse response=cartService.addFoodCart(customerId,foodCart);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);

    }
}
