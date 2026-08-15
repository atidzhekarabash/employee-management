package com.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.employeemanagement.model.Employee}
 */
public record EmployeeInsertRequest(@Size(max = 255) @NotBlank String firstName,
                                    @Size(max = 255) @NotBlank String middleName,
                                    @Size(max = 255) @NotBlank String lastName,
                                    @Size(min = 4, max = 255) @NotBlank String username,
                                    @Size(min = 8, max = 255) @NotBlank String password,
                                    @Size(max = 255) @Email @NotBlank String email,
                                    @NotNull UUID teamId) implements Serializable {

}