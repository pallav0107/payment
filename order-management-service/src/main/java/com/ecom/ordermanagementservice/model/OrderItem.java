package com.ecom.ordermanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ORDER_ITEM_ID")
  private long orderItemId;

  @ManyToOne
  @JoinColumn(name = "orderId")
  private Order order;

  @Column(name = "PRODUCT_ID")
  private long productId;
}
