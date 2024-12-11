package com.foodsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer restaurantId;
    private String orderDetails;
    private String orderStatus;
    private Double totalAmount;
    private LocalDateTime orderDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_Id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
            (
                    name = "Items_Orders",
                    joinColumns = @JoinColumn(name="order_Id"),
                    inverseJoinColumns = @JoinColumn(name="item_Id")
            )
    private List<Items> item;

    public Orders() {
    }
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer(Customer customer) {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<Items> getItem() {
        return item;
    }

    public void setItem(List<Items> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Orders{" +
                ", restaurantId=" + restaurantId +
                ", orderDetails='" + orderDetails + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", customer=" + customer +
                '}';
    }
}
