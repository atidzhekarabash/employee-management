package com.employeemanagement.model;

import com.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "description", nullable = false)
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  // TODO extend when implementing
}