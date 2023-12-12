package com.ecom.productmanagementservice.proxy;


import com.ecom.productmanagementservice.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryServiceProxy {

  @PostMapping("/inventory")
  ResponseEntity<InventoryDTO> addInventory(@RequestBody InventoryDTO inventoryDTO);
}
