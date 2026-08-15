package com.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record EmployeeUpdateRequest(@Size(max = 255) @NotBlank String firstName,
                                    @Size(max = 255) @NotBlank String middleName,
                                    @Size(max = 255) @NotBlank String lastName,
                                    @Size(min = 4, max = 255) @NotBlank String username,
                                    @Size(max = 255) @Email @NotBlank String email,
                                    @NotNull UUID teamId) {

}
