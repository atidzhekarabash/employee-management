package com.employeemanagement.model;

import com.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employment_history")
public class EmploymentHistory extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @NotNull
  @Column(name = "wage", nullable = false, precision = 10, scale = 2)
  private BigDecimal wage;

  @NotNull
  @Column(name = "date", nullable = false)
  private LocalDate date;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getWage() {
    return wage;
  }

  public void setWage(BigDecimal wage) {
    this.wage = wage;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  // TODO extend when implementing
}