package com.foodsystem.entity;

import jakarta.persistence.*;

@Entity


public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer adminId;
    private String   customerName;
    private String password;
    @Column(unique = true)
    private String   email;
    private String   phoneNumber;
    private String   address;

    public Admin() {
    }

    public Admin(String customerName, String password, String email, String phoneNumber, String address) {
        this.customerName = customerName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
