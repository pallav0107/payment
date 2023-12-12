package com.ecom.inventoryservice.controller;

import com.ecom.inventoryservice.dto.InventoryDTO;
import com.ecom.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@AllArgsConstructor
public class InventoryController {

  private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

  @Autowired
  private InventoryService inventoryService;

  @GetMapping(value = "/{productId}")
  public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable long productId) {
    return new ResponseEntity<>(inventoryService.getInventoryById(productId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<InventoryDTO> addInventory(
      @RequestBody @NonNull final InventoryDTO inventoryDTO) {
    log.info("Inside Inventory controller:: add new Inventory");
    return new ResponseEntity<>(inventoryService.addInventory(inventoryDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{productId}")
  public ResponseEntity<String> deleteInventory(@PathVariable long productId) {
    log.info("Inside Inventory controller:: delete Inventory");
    inventoryService.deleteInventory(productId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<InventoryDTO>> getAllOrders(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(inventoryService.getInventories(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

}
