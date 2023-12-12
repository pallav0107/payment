package com.ecom.shoppingcartservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "cart_events")
public class ShoppingCart {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "CARD_ID")
  private long cardId;

  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "PRODUCT_ID")
  private long producId;

  @Column(name = "QUANTITY")
  private int quantity;

  @Column(name = "TIMESTAMP")
  private LocalDateTime timestamp;

  @Column(name = "SESSION_ID")
  private long sessionId;

  @Column(name = "WHISHLIST_ID")
  private long whishlistId;

  @Column(name = "COUPON_APPLIED")
  private String couponApplied;

  @Column(name = "IS_ABANDONED")
  private boolean isAbandoned;

  @Column(name = "IS_PURCHASED")
  private boolean isPurchased;

}
