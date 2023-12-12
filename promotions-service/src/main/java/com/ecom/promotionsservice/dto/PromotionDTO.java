package com.ecom.promotionsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {

  private long discountId;
  private long productId;
  private int discountPercentage;
  private LocalDate startDate;
  private LocalDate endDate;
  private String couponCode;
  private boolean isActive;
  private int usageLimit;
  private String applicableCategory;
  private String applicableUserType;
}
