package com.ecom.ordermanagementservice.model;


import com.ecom.ordermanagementservice.constant.OrderStatus;
import com.ecom.ordermanagementservice.constant.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Order entity map to orders table
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @Column(name = "ORDER_ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long orderId;

  //@ManyToOne
  //@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
  @Column(name = "USER_ID")
  private long userID;

  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "ORDER_DATE")
  private LocalDateTime orderDate;

  @Column(name = "TOTAL_AMOUNT")
  private long totalAmount;

  @Column(name = "ORDER_STATUS")
  private OrderStatus orderStatus;

  @Column(name = "STATUS")
  private boolean status;

  @Column(name = "SHIPPING_ADDRESS")
  private String shippingAddress;

  @Column(name = "BILLING_ADDRESS")
  private String billingAddress;

  @Column(name = "PAYMENT_METHOD")
  private PaymentMethod paymentMethod;

  @Column(name = "DELIVERY_DATE")
  private LocalDate deliveryDate;

  @Column(name = "CUSTOMER_NOTES")
  private String customerNotes;

  @Column(name = "INVOICE_ID")
  private long invoiceId;

  //@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  //private List<OrderItem> orderItems;

}
