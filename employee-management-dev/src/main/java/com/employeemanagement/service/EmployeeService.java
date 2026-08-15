package com.employeemanagement.service;

import com.employeemanagement.dto.EmployeeInsertRequest;
import com.employeemanagement.dto.EmployeeResponse;
import com.employeemanagement.dto.EmployeeUpdateRequest;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

  EmployeeResponse getEmployeeById(UUID id);

  List<EmployeeResponse> getAllEmployees();

  UUID createEmployee(EmployeeInsertRequest employeeInsertRequest);

  void updateEmployee(UUID id, EmployeeUpdateRequest employeeUpdateRequest);

  void deleteEmployee(UUID id);
}
