package com.ecom.ordermanagementservice.listener;


import com.ecom.ordermanagementservice.constant.KafkaConstants;
import com.ecom.ordermanagementservice.constant.OrderStatus;
import com.ecom.ordermanagementservice.constant.PaymentMethod;
import com.ecom.ordermanagementservice.dto.OrderDTO;
import com.ecom.ordermanagementservice.dto.PaymentDTO;
import com.ecom.ordermanagementservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The type Attendance listener.
 */
@Component
public class PaymentListener {

  private static final Logger log = LoggerFactory.getLogger(PaymentListener.class);

  @Autowired
  OrderService orderService;

  /**
   * Attendance message consumer
   *
   * @param paymentDTO the paymentDTO
   */
  @KafkaListener(topics = KafkaConstants.TOPIC_NAME, groupId = KafkaConstants.GROUP_ID)
  public void consume(PaymentDTO paymentDTO) {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setOrderId(paymentDTO.getOrderId());
    orderDTO.setPaymentId(paymentDTO.getPaymentId());
    orderDTO.setProductId(paymentDTO.getProductId());
    orderDTO.setBillingAddress("TEST ADDRESS");
    orderDTO.setCustomerNotes("NOTES");
    orderDTO.setDeliveryDate(LocalDate.now());
    orderDTO.setInvoiceId(1234);
    orderDTO.setOrderStatus(OrderStatus.CREATED);
    orderDTO.setPaymentMethod(PaymentMethod.ONLINE);
    orderDTO.setShippingAddress("TEST SHIPPING ADDRESS");
    orderDTO.setStatus(true);
    orderDTO.setTotalAmount(paymentDTO.getAmount());
    orderDTO.setUserId(1);
    orderService.addOrder(orderDTO);
    log.info("Attendance Message received for transaction: ", paymentDTO.getTransactionId());
  }
}
