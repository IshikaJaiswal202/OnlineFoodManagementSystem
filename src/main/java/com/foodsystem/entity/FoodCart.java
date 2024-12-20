package com.foodsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class FoodCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer cartId;

    @OneToOne
    @JoinColumn(name = "customerId")
    private  Customer customer;

     @JsonIgnore
     @ManyToMany
     @JoinTable(name = "FoodCartWithItems", joinColumns =  @JoinColumn(name="foodCart_Id"), inverseJoinColumns = @JoinColumn(name = "item_Id"))
     private  List<Items> items;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
