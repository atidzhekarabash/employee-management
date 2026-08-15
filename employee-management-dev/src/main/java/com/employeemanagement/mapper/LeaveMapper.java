package com.employeemanagement.mapper;

import com.employeemanagement.dto.LeaveInsertRequest;
import com.employeemanagement.dto.LeaveResponse;
import com.employeemanagement.dto.LeaveUpdateRequest;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Leave;
import com.employeemanagement.model.common.LeaveState;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LeaveMapper {

  public LeaveResponse toLeaveResponse(Leave leave) {
    Objects.requireNonNull(leave);

    return new LeaveResponse(
        leave.getId(),
        leave.getRequestDate(),
        leave.getStartDate(),
        leave.getEndDate(),
        leave.getState(),
        leave.getType(),
        new LeaveResponse.EmployeeDetails(
            leave.getEmployee().getId(),
            leave.getEmployee().getFirstName(),
            leave.getEmployee().getMiddleName(),
            leave.getEmployee().getLastName(),
            leave.getEmployee().getEmail()
        )
    );
  }

  public Leave toLeave(LeaveInsertRequest leaveInsertRequest,
      LocalDate requestDate,
      LeaveState leaveState,
      Employee employee) {
    Objects.requireNonNull(leaveInsertRequest);
    Objects.requireNonNull(requestDate);
    Objects.requireNonNull(leaveState);
    Objects.requireNonNull(employee);

    return new Leave(
        requestDate,
        leaveInsertRequest.startDate(),
        leaveInsertRequest.endDate(),
        leaveState,
        leaveInsertRequest.type(),
        employee
    );
  }

  public Leave toLeave(LeaveUpdateRequest leaveUpdateRequest, Leave leave) {
    Objects.requireNonNull(leaveUpdateRequest);
    Objects.requireNonNull(leave);

    leave.setStartDate(leaveUpdateRequest.startDate());
    leave.setEndDate(leaveUpdateRequest.endDate());
    leave.setType(leaveUpdateRequest.type());

    return leave;
  }
}
