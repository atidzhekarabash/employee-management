package com.employeemanagement.exception.common;

public class DuplicateEntryException extends RuntimeException {


  public DuplicateEntryException(String message) {
    super(message);
  }

  public DuplicateEntryException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateEntryException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
