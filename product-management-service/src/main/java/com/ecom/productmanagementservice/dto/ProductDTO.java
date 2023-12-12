package com.ecom.productmanagementservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private long productId;
  private String name;
  private String description;
  private BigDecimal price;
  private String category;
  private String brand;
  private BigDecimal weight;
  private String dimensions;
  private String manufacturer;
  private boolean availability;
}
