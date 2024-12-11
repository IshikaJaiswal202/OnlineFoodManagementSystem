package com.foodsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer itemId;
    @Column(unique = true)
    private String itemName;
    private Double itemCost;
    private Integer  quantity;
    private String  isAvailable;
    private String category;
    @ManyToOne()
    @JoinColumn(name = "restaurant_Id")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "items",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<FoodCart> foodCart;

    @ManyToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private  List<Orders> orders;

    public Items() {
    }

    public Items(Integer itemId, String itemName, Double itemCost, Integer quantity, String isAvailable, String category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.category = category;
    }


    public void setItemName(String itemName) {

        this.itemName = itemName;
    }

    public List<FoodCart> getFoodCart() {
        return foodCart;
    }

    public void setFoodCart(List<FoodCart> foodCart) {
        this.foodCart = foodCart;
    }

    public Integer getItemId() {

        return itemId;
    }

    public void setItemId(Integer itemId) {

        this.itemId = itemId;
    }

    public String getItemName()
    {
        return itemName;
    }

    public Double getItemCost() {
        return itemCost;
    }

    public void setItemCost(Double itemCost) {

        this.itemCost = itemCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {

        this.isAvailable = isAvailable;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Items{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemCost=" + itemCost +
                ", quantity=" + quantity +
                ", isAvailable='" + isAvailable + '\'' +
                ", category=" + category +
                '}';
    }
}
