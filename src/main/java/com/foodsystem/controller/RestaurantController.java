package com.foodsystem.controller;

import com.foodsystem.entity.ApiResponse;
import com.foodsystem.entity.Items;
import com.foodsystem.service.IItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    IItemsService itemsService;
    //Items Operations

    @PostMapping("/addItems/{id}")
    public ResponseEntity<ApiResponse> addItems(@PathVariable Integer id,@RequestBody Items item)
    {
        ApiResponse response=itemsService.addItems(id,item);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteItem/{itemName}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable String itemName)
    {
        ApiResponse response=itemsService.removeItems(itemName);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @PutMapping("/updateItem/{itemName}")
    public ResponseEntity<ApiResponse> updateItems(@PathVariable String itemName,@RequestBody Items items)
    {
        ApiResponse response=itemsService.updateItems(itemName,items);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @PatchMapping("/IsItemAvailable/{itemName}/{isAvailable}")
    public ResponseEntity<ApiResponse> IsItemAvailable(@PathVariable String itemName,@PathVariable String isAvailable) {
        ApiResponse response = itemsService.IsItemAvailable(itemName,isAvailable);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Items>> getAllItems() {
        List<Items> items = itemsService.getAllItems();
        return new ResponseEntity<List<Items>>(items, HttpStatus.OK);
    }
    @GetMapping("/getItemByName/{itemName}")
    public ResponseEntity<Items> getItemByName(@PathVariable String itemName) {
        Items items = itemsService.getItemByName(itemName);
        return new ResponseEntity<Items>(items, HttpStatus.OK);
    }

}
