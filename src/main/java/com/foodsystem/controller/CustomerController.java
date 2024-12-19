package com.foodsystem.controller;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.Items;
import com.foodsystem.entity.Orders;
import com.foodsystem.entity.Restaurant;
import com.foodsystem.service.ICustomerService;
import com.foodsystem.service.IFoodCartService;
import com.foodsystem.service.IOrderService;
import com.foodsystem.service.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    IFoodCartService cartService;

    @Autowired
    IRestaurantService restaurantService;

    @Autowired
    IOrderService orderService;

    @Autowired
    ICustomerService customerService;

    //login
    @PostMapping("/loginCustomer")
    public ResponseEntity<ApiResponse> loginCustomer(@RequestBody Customer customer)
    {
        ApiResponse response = customerService.verifyForLogin(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/getRestaurantByName/{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String restaurantName) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }

    //getAllRestaurant
    @GetMapping("/getAllRestaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurant() {
        System.out.println("Inside controller");
        List<Restaurant> list = restaurantService.getAllRestaurant();
        return new ResponseEntity<List<Restaurant>>(list, HttpStatus.OK);
    }
    @PutMapping("/saveItemsToFoodCart/{restaurantId}/{itemId}/{customerId}")
    public ResponseEntity<List<Items>> saveItemsToFoodCart(@PathVariable Integer restaurantId ,@PathVariable Integer itemId,@PathVariable Integer customerId)
    {
        List<Items> item=cartService.saveItemsToFoodCart(restaurantId,itemId,customerId);
        return  new ResponseEntity<>(item,HttpStatus.CREATED);
    }

    @GetMapping("/getAllItems_FoodCart/{customerId}")
    public ResponseEntity<List<Items>> getRestaurantByName(@PathVariable Integer customerId) {
        List<Items> items = cartService.getRestaurantByName(customerId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("palaceOrder/{customerId}")
    public ResponseEntity<ApiResponse> palaceOrder(@PathVariable Integer customerId, @RequestBody Orders orders)
    {
          ApiResponse response=orderService.palaceOrder(customerId,orders);
         return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


}


