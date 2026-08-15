package com.employeemanagement.dto;


import com.employeemanagement.model.common.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.employeemanagement.model.Leave}
 */
public record LeaveUpdateRequest(@NotNull @FutureOrPresent LocalDate startDate,
                                 @NotNull @FutureOrPresent LocalDate endDate,
                                 @NotNull LeaveType type) implements Serializable {

}