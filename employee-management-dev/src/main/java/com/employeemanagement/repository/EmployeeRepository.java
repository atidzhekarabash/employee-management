package com.employeemanagement.repository;

import com.employeemanagement.model.Employee;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

  @Override
  @EntityGraph(value = "Employee.team")
  Optional<Employee> findById(UUID id);

  @Override
  @EntityGraph(value = "Employee.team")
  List<Employee> findAll();

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
