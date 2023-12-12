package com.ecom.reviewsservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "product_reviews")
public class Reviews {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "REVIEW_ID")
  private long reviewId;

  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "RATING")
  private int rating;

  @Column(name = "COMMENT")
  private String comment;

  @Column(name = "TIMESTAMP")
  private LocalDateTime timestamp;

  @Column(name = "IS_VERIFIED")
  private boolean isVerified;

  @Column(name = "IS_HELPFUL")
  private boolean isHelpful;

  @Column(name = "RESPONSE_ID")
  private long responseId;

  @Column(name = "REVIEWER_LOCATION")
  private String reviewerLocation;
}
