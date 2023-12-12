package com.ecom.inventoryservice.repository;

import com.ecom.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  @Transactional
  @Modifying
  @Query("update Inventory i set i.quantityAvailable = ?1 where i.productId = ?2")
  int updateInventory(int quantityAvailable, long productId);

}
