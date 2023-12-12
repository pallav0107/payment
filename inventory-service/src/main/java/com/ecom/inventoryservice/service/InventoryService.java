package com.ecom.inventoryservice.service;

import com.ecom.inventoryservice.constant.ErrorConstant;
import com.ecom.inventoryservice.dto.InventoryDTO;
import com.ecom.inventoryservice.model.Inventory;
import com.ecom.inventoryservice.repository.InventoryRepository;
import com.ecom.ordermanagementservice.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

  private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  public InventoryDTO addInventory(InventoryDTO inventoryDTO) {
    Inventory savedInventory =
        inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
    log.info("Inside Inventory Service:: inventory added successfully ");
    return modelMapper.map(savedInventory, InventoryDTO.class);
  }

  public void deleteInventory(long productId) {
    inventoryRepository.findById(productId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.INVENTORY_NOT_FOUND));
    log.info("Inside Inventory Service:: inventory deleted successfully with product {}",
        productId);
  }

  public List<InventoryDTO> getInventories(PageRequest pageRequest) {
    log.info("Inside Inventory Service:: get inventory");
    return inventoryRepository.findAll(pageRequest).stream()
        .map(inventory -> modelMapper.map(inventory, InventoryDTO.class)).toList();
  }

  public InventoryDTO getInventoryById(long productId) {
    Inventory inventory = inventoryRepository.findById(productId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.INVENTORY_NOT_FOUND));
    return modelMapper.map(inventory, InventoryDTO.class);
  }

  public void updateInventory(InventoryDTO inventoryDTO) {
    int availableQty = inventoryDTO.getQuantityAvailable() - inventoryDTO.getReorderQuantity();
    inventoryDTO.setQuantityAvailable(availableQty);
    inventoryRepository.updateInventory(inventoryDTO.getQuantityAvailable(),
        inventoryDTO.getProductId());
  }
}
