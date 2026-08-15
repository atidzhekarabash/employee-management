package com.employeemanagement.dto;

import com.employeemanagement.model.common.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.employeemanagement.model.Leave}
 */
public record LeaveInsertRequest(@NotNull @FutureOrPresent LocalDate startDate,
                                 @NotNull @FutureOrPresent LocalDate endDate,
                                 @NotNull LeaveType type,
                                 @NotNull UUID employeeId) implements Serializable {

}