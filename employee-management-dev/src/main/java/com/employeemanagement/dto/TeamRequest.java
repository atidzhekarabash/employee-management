package com.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.employeemanagement.model.Team}
 */
public record TeamRequest(@NotBlank @Size(min = 3, max = 255) String name,
                          UUID leadId) implements Serializable {

  public TeamRequest(String name) {
    this(name, null);
  }
}