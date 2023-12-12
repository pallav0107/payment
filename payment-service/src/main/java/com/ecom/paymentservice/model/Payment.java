package com.ecom.paymentservice.model;

import com.ecom.paymentservice.constant.CardType;
import com.ecom.paymentservice.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PAYMENT_ID")
  private long paymentId;

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ORDER_ID")
  private long orderId;

  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "AMOUNT")
  private BigDecimal amount;

  @Column(name = "PAYMENT_STATUS")
  private PaymentStatus paymentStatus;

  @Column(name = "TIMESTAMP")
  private LocalDateTime timestamp;

  @Column(name = "PAYMENT_GATEWAY")
  private String paymentGateway;

  @Column(name = "CARD_TYPE")
  private CardType cardType;

  @Column(name = "CARD_LAST_DIGITS")
  private int cardLastDigits;

  @Column(name = "BILLING_ADDRESS")
  private String billingAddress;

  @Column(name = "TRANSACTION_ID")
  private String transactionId;

  @Column(name = "CURRENCY")
  private String currency;
}
