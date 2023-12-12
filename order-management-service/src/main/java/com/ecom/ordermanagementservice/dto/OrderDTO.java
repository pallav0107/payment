package com.ecom.ordermanagementservice.dto;


import com.ecom.ordermanagementservice.constant.OrderStatus;
import com.ecom.ordermanagementservice.constant.PaymentMethod;
import com.ecom.ordermanagementservice.constant.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  private long orderId;
  private long userId;
  private long productId;
  private LocalDateTime orderDate;
  private long totalAmount;
  private PaymentStatus paymentStatus;
  private OrderStatus orderStatus;
  private boolean status;
  private String shippingAddress;
  private String billingAddress;
  private PaymentMethod paymentMethod;
  private LocalDate deliveryDate;
  private String customerNotes;
  private long invoiceId;
  private String cvc;
  private Long expMonth;
  private Long expYear;
  private String number;
  private long paymentId;
  private Long amount;
  private String currency;
  //private List<OrderItem> orderItems;
}
