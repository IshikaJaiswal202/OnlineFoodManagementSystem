package com.foodsystem.service.impl;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.entity.Customer;
import com.foodsystem.entity.FoodCart;
import com.foodsystem.entity.Items;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import com.foodsystem.repo.ICustomerRepo;
import com.foodsystem.repo.IFoodCartRepo;
import com.foodsystem.repo.IItemsRepo;
import com.foodsystem.service.IFoodCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodCartServiceImpl implements IFoodCartService {

    @Autowired
    ICustomerRepo repo;

    @Autowired
    IItemsRepo itemsRepo;

    @Autowired
    IFoodCartRepo foodRepo;
    @Override
    public ApiResponse saveFoodCart(Integer customerId) {
        return null;
    }

    @Override
    public ApiResponse addFoodCart(Integer customerId, FoodCart foodCart) {
        Customer customer = repo.findById(customerId).orElseThrow(() -> new ResourceNotFoundExceptions("No Customer Found By This ID"));
        Optional<FoodCart> foodCarts = foodRepo.findByCustomerCustomerId(customerId);//.orElseThrow(() -> new ResourceNotFoundExceptions("A Customer Has Only One FoodCart"));
        if(foodCarts.isPresent())
        {
            throw  new ResourceNotFoundExceptions("A Customer Has Only One FoodCart");
        }
        foodCart.setCustomer(customer);
        FoodCart save = foodRepo.save(foodCart);
        return  new ApiResponse.Builder().
                msg("Successfully FoodCart is Created").
                code(HttpStatus.CREATED).
                success(true).
                build();
    }

    @Override
    public List<Items> saveItemsToFoodCart(Integer restaurantId, Integer itemId,Integer customerId) {

        Items items=itemsRepo.findByRestaurantIdAndItemId(restaurantId,itemId).orElseThrow(() -> new ResourceNotFoundExceptions("No Item Found By This ID"));
 //        List<Items> items = restaurant.getItems();
//        items.stream().filter(i->i.getItemId().equals(itemId)).findFirst().orElseThrow();
        FoodCart cart = foodRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundExceptions("No Customer Found By This ID"));;
        cart.getItems().add(items);
        FoodCart save = foodRepo.save(cart);
        System.out.println(save.getItems());
       List<Items> itemsList=save.getItems();
       System.out.println(itemsList);
        return itemsList;
    }

    @Override
    public List<Items> getRestaurantByName(Integer customerId) {

        return List.of();
    }
}
