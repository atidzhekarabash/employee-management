package com.employeemanagement.model;

import com.employeemanagement.model.common.BaseEntity;
import com.employeemanagement.model.common.LeaveActionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "leave_action",
    uniqueConstraints = @UniqueConstraint(columnNames = {"leave_id", "employee_id"}))
public class LeaveAction extends BaseEntity {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private LeaveActionType type;

  @NotNull
  @Column(name = "date", nullable = false)
  private LocalDate date;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "leave_id", nullable = false)
  private Leave leave;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  public LeaveActionType getType() {
    return type;
  }

  public void setType(LeaveActionType type) {
    this.type = type;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Leave getLeave() {
    return leave;
  }

  public void setLeave(Leave leave) {
    this.leave = leave;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}