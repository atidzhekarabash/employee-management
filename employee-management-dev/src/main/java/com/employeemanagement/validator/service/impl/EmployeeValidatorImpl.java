package com.employeemanagement.validator.service.impl;

import com.employeemanagement.dto.EmployeeInsertRequest;
import com.employeemanagement.dto.EmployeeUpdateRequest;
import com.employeemanagement.exception.common.DuplicateEntryException;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.common.EmployeeValidator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorImpl implements EmployeeValidator {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public EmployeeValidatorImpl(TeamRepository teamRepository, EmployeeRepository employeeRepository,
      MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
    this.messageUtil = messageUtil;
  }

  @Override
  public void validateCreation(EmployeeInsertRequest request) {
    Objects.requireNonNull(request);
    checkIfTeamExists(request.teamId());
    checkIfDuplicateUsername(request.username());
    checkIfDuplicateEmail(request.email());
  }

  @Override
  public void validateUpdate(EmployeeUpdateRequest request, Employee entity) {
    Objects.requireNonNull(request);
    Objects.requireNonNull(entity);
    if (!Objects.equals(request.username(), entity.getUsername())) {
      checkIfDuplicateUsername(request.username());
    }
    if (!Objects.equals(request.email(), entity.getEmail())) {
      checkIfDuplicateEmail(request.email());
    }
    checkIfTeamExists(request.teamId());
  }

  @Override
  public void validateDeletion(UUID id) {
    Objects.requireNonNull(id);
    checkIfEmployeeExists(id);
  }

  private void checkIfEmployeeExists(UUID id) {
    if (!employeeRepository.existsById(id)) {
      throw new NotFoundException(messageUtil.getMessage("employee.not_found", id));
    }
  }

  private void checkIfTeamExists(UUID teamId) {
    if (!teamRepository.existsById(teamId)) {
      throw new RelatedEntityNotFoundException(messageUtil.getMessage("team.not_found", teamId));
    }
  }

  private void checkIfDuplicateUsername(String username) {
    if (employeeRepository.existsByUsername(username)) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.username", username)
      );
    }
  }

  private void checkIfDuplicateEmail(String email) {
    if (employeeRepository.existsByEmail(email)) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("employee.duplicate.email", email)
      );
    }
  }
}
