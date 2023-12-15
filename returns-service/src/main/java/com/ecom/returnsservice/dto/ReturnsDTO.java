package com.ecom.returnsservice.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnsDTO {

  private long returnId;
  private long orderId;

  private long userId;

  private String returnReason;

  private boolean status;
  private LocalDateTime timestamp;

  private int return_quantity;

  private String returnNotes;
  private String returnAddress;
  private long refundAmount;
}
