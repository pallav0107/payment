package com.ecom.ordermanagementservice.controller;

import com.ecom.ordermanagementservice.dto.OrderDTO;
import com.ecom.ordermanagementservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

  private static final Logger log = LoggerFactory.getLogger(OrderController.class);

  @Autowired
  private OrderService orderService;


  /**
   * Add order response entity.
   *
   * @param orderDTO the order dto
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<OrderDTO> addOrder(@RequestBody @NonNull final OrderDTO orderDTO) {
    log.info("Inside Order controller:: add new order");
    return orderService.addOrder(orderDTO);
  }

  /**
   * Delete order
   *
   * @param orderId orderId
   */
  @DeleteMapping(value = "/{orderId}")
  public ResponseEntity<String> deleteOrder(@PathVariable long orderId) {
    log.info("Inside Order controller:: delete order");
    orderService.deleteOrder(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Gets all orders.
   *
   * @param page the page
   * @param size the size
   * @return the all orders
   */
  @GetMapping
  public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(orderService.getOrders(PageRequest.of(page, size)), HttpStatus.OK);
  }

  /**
   * Gets order by id.
   *
   * @param orderId the orderId
   * @return the order by id
   */
  @GetMapping(value = "/{orderId}")
  public ResponseEntity<OrderDTO> getOrderById(@PathVariable long orderId) {
    return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
  }

}
