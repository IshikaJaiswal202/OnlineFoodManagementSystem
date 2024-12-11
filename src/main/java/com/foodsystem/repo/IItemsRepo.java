package com.foodsystem.repo;

import com.foodsystem.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IItemsRepo extends JpaRepository<Items ,Integer> {
   public Optional<Items> findByItemName(String itemName);

    public void deleteByItemName(String itemName);

   public  List<Items> findByRestaurantRestaurantId(Integer restaurantId);

    @Query("SELECT i FROM Items i WHERE i.restaurant.id = :restaurantId AND i.itemId = :itemId")
    Optional<Items> findByRestaurantIdAndItemId(@Param("restaurantId") Integer restaurantId, @Param("itemId") Integer itemId);
}
