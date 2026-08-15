package com.employeemanagement.validator.service;

import com.employeemanagement.dto.LeaveInsertRequest;
import com.employeemanagement.dto.LeaveUpdateRequest;
import com.employeemanagement.model.Leave;
import com.employeemanagement.validator.service.common.CreateValidator;
import com.employeemanagement.validator.service.common.DeleteValidator;

public interface LeaveValidator extends
    CreateValidator<LeaveInsertRequest>,
    UpdateValidator<LeaveUpdateRequest, Leave>,
    DeleteValidator {

}
