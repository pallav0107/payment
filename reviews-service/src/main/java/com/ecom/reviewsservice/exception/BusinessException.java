package com.ecom.reviewsservice.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, long id) {
    this(message + ": " + id);
  }
}
