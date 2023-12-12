package com.ecom.ordermanagementservice.service;


import com.ecom.ordermanagementservice.config.KafkaPublisher;
import com.ecom.ordermanagementservice.constant.ErrorConstant;
import com.ecom.ordermanagementservice.constant.OrderStatus;
import com.ecom.ordermanagementservice.dto.InventoryDTO;
import com.ecom.ordermanagementservice.dto.OrderDTO;
import com.ecom.ordermanagementservice.dto.PaymentDTO;
import com.ecom.ordermanagementservice.exception.BusinessException;
import com.ecom.ordermanagementservice.model.Order;
import com.ecom.ordermanagementservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
  private static final Logger log = LoggerFactory.getLogger(OrderService.class);
  @Autowired
  private final OrderRepository orderRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private KafkaPublisher kafkaPublisher;

  public ResponseEntity<OrderDTO> addOrder(OrderDTO orderDTO) {
    orderDTO.setOrderDate(LocalDateTime.now());
    Order savedOrder = orderRepository.save(modelMapper.map(orderDTO, Order.class));
    log.info("Inside Order Service:: order added successfully ");

    // Kafka logic
    InventoryDTO inventoryDTO = new InventoryDTO();
    inventoryDTO.setProductId(orderDTO.getProductId());
    inventoryDTO.setReorderQuantity(1);
    inventoryDTO.setQuantityAvailable(100);
    kafkaPublisher.sendMessage(inventoryDTO);
    return new ResponseEntity<OrderDTO>(modelMapper.map(savedOrder, OrderDTO.class),
        HttpStatus.CREATED);
  }

  public void deleteOrder(long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found with Id"));
    order.setStatus(false);
    log.info("Inside Order Service:: order deleted successfully with orderId {}", orderId);
  }

  public List<OrderDTO> getOrders(PageRequest pageRequest) {
    log.info("Inside Order Service:: get orders");
    return orderRepository.findAll(pageRequest).stream()
        .map(order -> modelMapper.map(order, OrderDTO.class)).toList();
  }

  public OrderDTO getOrderById(long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.ORDER_NOT_FOUND));
    return modelMapper.map(order, OrderDTO.class);
  }

  public void confirmOrder(PaymentDTO paymentDTO) {
    int status = orderRepository.updateOrderStatus(OrderStatus.PLACED, paymentDTO.getOrderId());
    log.info("Inside Order Service:: confirm order status {}", status);
  }
}
