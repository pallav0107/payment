package com.ecom.customersupportservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

  @Getter
  @Setter
  @Entity
  @Table(name = "SUPPORT_INTERACTIONS")
  public class CustomerSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INTERACTION_ID")
    private long interactionId;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "ISSUE_TYPE")
    private String issueType;

    @Column(name = "RESOLVED_STATUS")
    private boolean resolvedStatus;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "TICKET_DATE")
    private LocalDateTime ticketDate;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "RESPONSE_TIME")
    private LocalDateTime responseTime;

    @Column(name = "RESOLUTION_NOTES")
    private long resolution_notes;

  }
