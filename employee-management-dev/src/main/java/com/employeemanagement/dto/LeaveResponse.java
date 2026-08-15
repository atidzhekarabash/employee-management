package com.employeemanagement.dto;

import com.employeemanagement.model.common.LeaveState;
import com.employeemanagement.model.common.LeaveType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.employeemanagement.model.Leave}
 */
public record LeaveResponse(UUID id,
                            LocalDate requestDate,
                            LocalDate startDate,
                            LocalDate endDate,
                            LeaveState state,
                            LeaveType type,
                            EmployeeDetails employee) implements Serializable {

  public record EmployeeDetails(UUID id,
                                String firstName,
                                String middleName,
                                String lastName,
                                String email) implements Serializable {

  }
}