package com.ecom.ordermanagementservice.config;


import com.ecom.ordermanagementservice.dto.PaymentDTO;
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

  /**
   * Consumer factory.
   *
   * @return the consumer factory
   */
  @Bean
  public ConsumerFactory<String, PaymentDTO> consumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,
        "com.ecom.ordermanagementservice.dto.PaymentDTO");
    return new DefaultKafkaConsumerFactory<>(props);
  }

  /**
   * Kafka listener container factory concurrent kafka listener container factory.
   *
   * @return the concurrent kafka listener container factory
   */
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PaymentDTO> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, PaymentDTO> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
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
