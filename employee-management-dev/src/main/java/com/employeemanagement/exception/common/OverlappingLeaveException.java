package com.employeemanagement.exception.common;

import java.time.LocalDate;
import java.util.List;

public class OverlappingLeaveException extends RuntimeException {

  private final List<LocalDate[]> overlappingPeriods;

  public OverlappingLeaveException(List<LocalDate[]> overlappingPeriods) {
    this.overlappingPeriods = overlappingPeriods;
  }

  public List<LocalDate[]> getOverlappingPeriods() {
    return overlappingPeriods;
  }
}
