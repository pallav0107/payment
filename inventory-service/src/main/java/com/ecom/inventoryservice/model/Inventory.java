package com.ecom.inventoryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Inventory entity map to product table
 */
@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {

  @Id
  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "QUANTITY_AVAILABLE")
  private int quantityAvailable;

  @Column(name = "LAST_UPDATED")
  private LocalDate lastUpdated;

  @Column(name = "VENDOR")
  private String vendor;

  @Column(name = "MIN_STOCK_THRESHOLD")
  private long minStockThreshold;

  @Column(name = "MAX_STOCK_THRESHOLD")
  private long maxStockThreshold;

  @Column(name = "REORDER_QUANTITY")
  private int reorderQuantity;

  @Column(name = "COST_PRICE")
  private long costPrice;

  @Column(name = "SUPPLIER_ID")
  private BigDecimal supplierId;

}
