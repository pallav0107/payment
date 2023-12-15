package com.ecom.customersupportservice.dto;



import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSupportDTO {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "INTERACTION_ID")
  private long interactionId;

  private long userId;

  private String issueType;

  private boolean resolvedStatus;

  private LocalDateTime ticketDate;

  private String agentId;

  private String priority;

  private String department;

  private LocalDateTime responseTime;

  private long resolution_notes;
}
