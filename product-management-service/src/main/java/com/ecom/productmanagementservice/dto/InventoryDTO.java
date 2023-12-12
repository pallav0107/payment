package com.ecom.productmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
  private long productId;
  private int quantityAvailable;
  private LocalDate lastUpdated;
  private String vendor;
  private long minStockThreshold;
  private long maxStockThreshold;
  private int reorderQuantity;
  private BigDecimal costPrice;
  private BigDecimal supplierId;
}
