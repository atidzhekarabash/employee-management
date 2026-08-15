package com.employeemanagement.validator.service.common;

public interface CreateValidator<T> {

  void validateCreation(T request);
}
