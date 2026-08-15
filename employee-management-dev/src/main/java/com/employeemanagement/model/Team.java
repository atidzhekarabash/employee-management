package com.employeemanagement.model;

import com.employeemanagement.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team extends BaseEntity {

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lead_id", unique = true)
  private Employee lead;

  @OneToMany(mappedBy = "team")
  private List<Employee> employees = new ArrayList<>();

  public Team() {
  }

  public Team(String name) {
    this.name = name;
  }

  public Team(String name, Employee employee) {
    this.name = name;
    this.lead = employee;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee getLead() {
    return lead;
  }

  public void setLead(Employee lead) {
    this.lead = lead;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
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