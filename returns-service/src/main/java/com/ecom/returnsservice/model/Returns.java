package com.ecom.returnsservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "returns")
public class Returns {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "RETURN_ID")
  private long returnId;

  @Column(name = "ORDER_ID")
  private long orderId;

  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "RETURN_REASON")
  private String returnReason;

  @Column(name = "STATUS")
  private boolean status;

  @Column(name = "RETURN_DATE")
  private LocalDateTime returnDate;

  @Column(name = "RETURN_QUANTITY")
  private int returnQuantity;

  @Column(name = "RETURN_NOTES")
  private String returnNotes;

  @Column(name = "RETURN_ADDRESS")
  private String returnAddress;

  @Column(name = "REFUND_AMOUNT")
  private long refundAmount;

}
