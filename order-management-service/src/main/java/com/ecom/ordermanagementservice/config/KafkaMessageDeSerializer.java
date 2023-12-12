package com.ecom.ordermanagementservice.config;

import com.ecom.ordermanagementservice.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * The type Kafka message de serializer.
 */
public class KafkaMessageDeSerializer extends ErrorHandlingDeserializer<PaymentDTO>
    implements Deserializer<PaymentDTO> {

  private final ObjectMapper objectMapper =
      new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
          .registerModule(new JavaTimeModule());

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    KafkaMessageDeSerializer.super.configure(configs, isKey);
  }

  @Override
  public PaymentDTO deserialize(String string, byte[] bytes) {
    try {
      return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), PaymentDTO.class);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
