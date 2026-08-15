package com.employeemanagement.validator.service.common;

import com.employeemanagement.dto.EmployeeInsertRequest;
import com.employeemanagement.dto.EmployeeUpdateRequest;
import com.employeemanagement.model.Employee;
import com.employeemanagement.validator.service.UpdateValidator;

public interface EmployeeValidator extends
    CreateValidator<EmployeeInsertRequest>,
    UpdateValidator<EmployeeUpdateRequest, Employee>,
    DeleteValidator {

}
