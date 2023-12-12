package com.ecom.productmanagementservice.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Product entity map to product table
 */
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "NAME")
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CATEGORY")
  private String category;

  @Column(name = "BRAND")
  private String brand;

  @Column(name = "WEIGHT")
  private BigDecimal weight;

  @Column(name = "DIMENSIONS")
  private String dimensions;

  @Column(name = "MANUFACTURER")
  private String manufacturer;

  @Column(name = "AVAILABILITY")
  private boolean availability;
}
