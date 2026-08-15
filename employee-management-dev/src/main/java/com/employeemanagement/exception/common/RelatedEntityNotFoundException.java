package com.employeemanagement.exception.common;

public class RelatedEntityNotFoundException extends RuntimeException {

  public RelatedEntityNotFoundException(String message) {
    super(message);
  }

  public RelatedEntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public RelatedEntityNotFoundException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
