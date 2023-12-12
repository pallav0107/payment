package com.ecom.inventoryservice.config;


import com.ecom.inventoryservice.constant.KafkaConstants;
import com.ecom.inventoryservice.dto.InventoryDTO;
import com.ecom.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The type Order listener.
 */
@Component
public class OrderListener {

  private static final Logger log = LoggerFactory.getLogger(OrderListener.class);

  @Autowired
  InventoryService inventoryService;

  /**
   * Order message consumer
   *
   * @param inventoryDTO the inventoryDTO
   */
  @KafkaListener(topics = KafkaConstants.TOPIC_NAME, groupId = KafkaConstants.GROUP_ID)
  public void consume(InventoryDTO inventoryDTO) {
    inventoryService.updateInventory(inventoryDTO);
    log.info("Inventory Message received for product: {}", inventoryDTO.getProductId());
  }
}
