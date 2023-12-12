package com.ecom.shoppingcartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

  private long cardId;
  private long userId;
  private long producId;
  private int quantity;
  private LocalDateTime timestamp;
  private long sessionId;
  private long whishlistId;
  private String couponApplied;
  private boolean isAbandoned;
  private boolean isPurchased;

}
