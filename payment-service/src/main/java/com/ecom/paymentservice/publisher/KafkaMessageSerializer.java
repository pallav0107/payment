package com.ecom.paymentservice.publisher;


import com.ecom.paymentservice.dto.PaymentDTO;
import com.ecom.paymentservice.exception.BusinessException;
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
public class KafkaMessageSerializer implements Serializer<PaymentDTO> {

  private final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
          .registerModule(new JavaTimeModule());

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    Serializer.super.configure(configs, isKey);
  }

  @Override
  public byte[] serialize(String s, PaymentDTO paymentDTO) {
    try {
      return objectMapper.writeValueAsBytes(paymentDTO);
    } catch (JsonProcessingException e) {
      throw new BusinessException(e.getMessage());
    }
  }
}
