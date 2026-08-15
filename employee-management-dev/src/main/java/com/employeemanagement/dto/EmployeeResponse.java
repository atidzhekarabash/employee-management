package com.employeemanagement.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.employeemanagement.model.Employee}
 */
public record EmployeeResponse(UUID id,
                               String firstName,
                               String middleName,
                               String lastName,
                               String email,
                               TeamResponse team) implements Serializable {

}