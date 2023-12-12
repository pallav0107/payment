package com.ecom.reviewsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDTO {
  private long reviewId;

  private long productId;

  private long userId;

  private int rating;

  private String comment;

  private LocalDateTime timestamp;

  private boolean isVerified;

  private boolean isHelpful;

  private long responseId;

  private String reviewerLocation;
}
