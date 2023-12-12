package com.ecom.ordermanagementservice.config;



import com.ecom.ordermanagementservice.constant.KafkaConstants;
import com.ecom.ordermanagementservice.dto.InventoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka Message publisher
 */
@Component
public class KafkaPublisher {
  private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);
  @Autowired
  KafkaTemplate<String, InventoryDTO> kafkaTemplate;

  public void sendMessage(InventoryDTO inventoryDTO) {
    log.info("Inside kafka publisher:: sending message to topic: topic name: {} message {}",
        KafkaConstants.ORDER_TOPIC, inventoryDTO);
    kafkaTemplate.send(KafkaConstants.ORDER_TOPIC, inventoryDTO);
  }

}
