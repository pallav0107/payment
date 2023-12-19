package com.ecom.inventoryservice.config;


import com.ecom.inventoryservice.dto.InventoryDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Kafka consumer config.
 */
@Configuration
public class KafkaConsumerConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;


  @Value(value = "${spring.kafka.consumer.group-id}")
  private String consumerGroupId;

  @Value(value = "${spring.kafka.consumer.product-group-id}")
  private String productConsumerGroupId;

  /**
   * Consumer factory.
   *
   * @return the consumer factory
   */
  @Bean
  public ConsumerFactory<String, InventoryDTO> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecom.inventoryservice.dto.InventoryDTO");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConsumerFactory<String, InventoryDTO> consumerFactory2() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, productConsumerGroupId);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecom.inventoryservice.dto.InventoryDTO");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  /**
   * Kafka listener container factory concurrent kafka listener container factory.
   *
   * @return the concurrent kafka listener container factory
   */
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, InventoryDTO> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, InventoryDTO> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, InventoryDTO> kafkaListenerContainerFactory2() {
    ConcurrentKafkaListenerContainerFactory<String, InventoryDTO> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory2());
    return factory;
  }

  /**
   * Json converter string json message converter.
   *
   * @return the string json message converter
   */
  @Bean
  public StringJsonMessageConverter jsonConverter() {
    return new StringJsonMessageConverter();
  }

}
