package com.ecom.productmanagementservice.config;


import com.ecom.productmanagementservice.dto.InventoryDTO;
import com.ecom.productmanagementservice.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * The type Kafka message serializer.
 */
public class KafkaMessageSerializer implements Serializer<InventoryDTO> {

  private final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
          .registerModule(new JavaTimeModule());

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    Serializer.super.configure(configs, isKey);
  }

  @Override
  public byte[] serialize(String s, InventoryDTO inventoryDTO) {
    try {
      return objectMapper.writeValueAsBytes(inventoryDTO);
    } catch (JsonProcessingException e) {
      throw new BusinessException(e.getMessage());
    }
  }
}
