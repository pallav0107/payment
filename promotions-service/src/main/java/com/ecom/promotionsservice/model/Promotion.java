package com.ecom.promotionsservice.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Order entity map to product table
 */
@Getter
@Setter
@Entity
@Table(name = "discounts")
public class Promotion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "DISCOUNT_ID")
  private long discountId;
  @Column(name = "PRODUCT_ID")
  private long productId;
  @Column(name = "DISCOUNT_PERCENTAGE")
  private int discountPercentage;
  @Column(name = "START_DATE")
  private LocalDate startDate;
  @Column(name = "END_DATE")
  private LocalDate endDate;
  @Column(name = "COUPON_CODE")
  private String couponCode;
  @Column(name = "IS_ACTIVE")
  private boolean isActive;
  @Column(name = "USAGE_LIMIT")
  private int usageLimit;
  @Column(name = "APPLICABLE_CATEGORY")
  private String applicableCategory;
  @Column(name = "APPLICABLE_USER_TYPE")
  private String applicableUserType;
}
