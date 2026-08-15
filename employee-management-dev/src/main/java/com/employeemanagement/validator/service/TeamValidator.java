package com.employeemanagement.validator.service;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.model.Team;
import com.employeemanagement.validator.service.common.CreateValidator;
import com.employeemanagement.validator.service.common.DeleteValidator;

public interface TeamValidator extends
    CreateValidator<TeamRequest>,
    UpdateValidator<TeamRequest, Team>,
    DeleteValidator {

}
