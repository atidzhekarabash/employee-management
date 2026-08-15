package com.employeemanagement.validator.service.impl;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.exception.common.DuplicateEntryException;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.employeemanagement.exception.common.TeamNotEmptyException;
import com.employeemanagement.model.Team;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.TeamValidator;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TeamValidatorImpl implements TeamValidator {

  private final TeamRepository teamRepository;
  private final EmployeeRepository employeeRepository;
  private final MessageUtil messageUtil;

  public TeamValidatorImpl(TeamRepository teamRepository,
      EmployeeRepository employeeRepository,
      MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.employeeRepository = employeeRepository;
    this.messageUtil = messageUtil;
  }

  @Override
  public void validateCreation(TeamRequest request) {
    Objects.requireNonNull(request);
    checkIfDuplicateTeamName(request);
    if (Objects.nonNull(request.leadId())) {
      checkIfEmployeeExists(request.leadId());
      checkIfDuplicateLead(request);
    }
  }

  @Override
  public void validateUpdate(TeamRequest request, Team entity) {
    Objects.requireNonNull(request);
    Objects.requireNonNull(entity);
    if (!Objects.equals(entity.getName(), request.name())) {
      checkIfDuplicateTeamName(request);
    }

    if (Objects.nonNull(request.leadId()) && isEntityLeadDifferent(request, entity)) {
      checkIfEmployeeExists(request.leadId());
      checkIfDuplicateLead(request);
    }
  }

  @Override
  public void validateDeletion(UUID id) {
    Objects.requireNonNull(id);
    checkIfTeamExists(id);
    checkIfTeamIsEmpty(id);
  }

  private void checkIfTeamExists(UUID leadId) {
    if (!teamRepository.existsById(leadId)) {
      throw new NotFoundException(messageUtil.getMessage("team.not_found", leadId));
    }
  }

  private void checkIfEmployeeExists(UUID leadId) {
    if (!employeeRepository.existsById(leadId)) {
      throw new RelatedEntityNotFoundException(
          messageUtil.getMessage("employee.not_found", leadId));
    }
  }

  private void checkIfDuplicateLead(TeamRequest teamRequest) {
    UUID leadId = teamRequest.leadId();

    if (teamRepository.existsByLead_Id(leadId)) {
      throw new DuplicateEntryException(messageUtil.getMessage("team.duplicate.lead", leadId));
    }
  }

  private void checkIfDuplicateTeamName(TeamRequest teamRequest) {
    if (teamRepository.existsByName(teamRequest.name())) {
      throw new DuplicateEntryException(
          messageUtil.getMessage("team.duplicate.name", teamRequest.name()));
    }
  }

  private void checkIfTeamIsEmpty(UUID id) {
    if (teamRepository.hasEmployees(id)) {
      throw new TeamNotEmptyException(messageUtil.getMessage("team.not_empty", id));
    }
  }

  private boolean isEntityLeadDifferent(TeamRequest request, Team entity) {
    if (Objects.isNull(entity.getLead())) {
      return true;
    }

    return !request.leadId().equals(entity.getLead().getId());
  }
}