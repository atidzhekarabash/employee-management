package com.employeemanagement.exception.common;

public class TeamNotEmptyException extends RuntimeException {

  public TeamNotEmptyException(String message) {
    super(message);
  }

  public TeamNotEmptyException(String message, Throwable cause) {
    super(message, cause);
  }

  public TeamNotEmptyException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
