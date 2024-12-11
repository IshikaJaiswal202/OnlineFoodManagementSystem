package com.foodsystem.repo;

import com.foodsystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepo extends JpaRepository<Orders , Integer>
{

}
