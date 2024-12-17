package com.foodsystem.repo;

import com.foodsystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepo extends JpaRepository<Customer,Integer> {
      public Optional<Customer> findByEmail(String email);

      public Customer findByCustomerName(String name);
      public  void deleteByEmail(String email);
}
