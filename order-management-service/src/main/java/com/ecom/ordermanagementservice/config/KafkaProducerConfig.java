package com.ecom.ordermanagementservice.config;


import com.ecom.ordermanagementservice.dto.InventoryDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer Configuration file
 */
@Configuration
public class KafkaProducerConfig {

  /**
   * kafka Bootstrap server address
   */
  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  /**
   * Kafka producer factory bean with custom kafka message serializer
   *
   * @return ProducerFactory producer factory
   */
  @Bean
  public ProducerFactory<String, InventoryDTO> producerFactory() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        KafkaMessageSerializer.class.getName());
    return new DefaultKafkaProducerFactory<>(properties);
  }

  /**
   * KafkaTemplate for sending message to kafka
   *
   * @return KafkaTemplate kafka template
   */
  @Bean
  public KafkaTemplate<String, InventoryDTO> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}
