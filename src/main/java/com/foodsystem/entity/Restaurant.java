package com.foodsystem.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;
    @Column(unique = true)
    private String  restaurantName;
    private String description;
    private Boolean Status;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Items> items;

    public Restaurant() {
    }

    public Restaurant(Integer restaurantId, String restaurantName, String description, Boolean status, List<Items> items) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.description = description;
        Status = status;
        this.items = items;
    }

    public Restaurant(Integer restaurantId, String restaurantName, String description, Boolean status) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.description = description;
        Status = status;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", description='" + description + '\'' +
                ", Status=" + Status +
                ", items=" + items +
                '}';
    }
}
