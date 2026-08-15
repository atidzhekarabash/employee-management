package com.employeemanagement.model;

import com.employeemanagement.model.common.BaseEntity;
import com.employeemanagement.model.common.LeaveState;
import com.employeemanagement.model.common.LeaveType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "`leave`",
    uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "start_date", "end_date"}))
@NamedEntityGraph(name = "Leave.employee", attributeNodes = {@NamedAttributeNode("employee")})
public class Leave extends BaseEntity {

  @NotNull
  @Column(name = "request_date", nullable = false)
  private LocalDate requestDate;

  @NotNull
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @NotNull
  @ColumnDefault("'PENDING'")
  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private LeaveState state;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private LeaveType type;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @OneToMany(mappedBy = "leave", cascade = CascadeType.REMOVE)
  private List<LeaveAction> leaveActions = new ArrayList<>();

  public Leave() {
  }

  public Leave(LocalDate requestDate, LocalDate startDate, LocalDate endDate, LeaveState state,
      LeaveType type, Employee employee) {
    this.requestDate = requestDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.state = state;
    this.type = type;
    this.employee = employee;
  }

  public LocalDate getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(LocalDate requestDate) {
    this.requestDate = requestDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LeaveState getState() {
    return state;
  }

  public void setState(LeaveState state) {
    this.state = state;
  }

  public LeaveType getType() {
    return type;
  }

  public void setType(LeaveType type) {
    this.type = type;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public List<LeaveAction> getLeaveActions() {
    return leaveActions;
  }

  public void setLeaveActions(List<LeaveAction> leaveActions) {
    this.leaveActions = leaveActions;
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