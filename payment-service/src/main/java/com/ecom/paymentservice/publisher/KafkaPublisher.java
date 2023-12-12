package com.ecom.paymentservice.publisher;



import com.ecom.paymentservice.dto.PaymentDTO;
import com.ecom.paymentservice.exception.BusinessException;
import org.apache.kafka.common.config.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka Message publisher
 */
@Component
public class KafkaPublisher {
  private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);
  @Autowired
  KafkaTemplate<String, PaymentDTO> kafkaTemplate;
  @Value(value = "${spring.kafka.topic}")
  private String topic;

  public void sendMessage(PaymentDTO paymentDTO) {
    log.info("Inside kafka publisher:: sending message to topic: topic name: {} message {}", topic,
        paymentDTO);
    try {
      kafkaTemplate.send(topic, paymentDTO);
    } catch (ConfigException e) {
      throw new BusinessException("Error in send message: " + e.getMessage());
    }

  }

}
