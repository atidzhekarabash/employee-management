package com.employeemanagement.service.impl;

import com.employeemanagement.dto.EmployeeInsertRequest;
import com.employeemanagement.dto.EmployeeResponse;
import com.employeemanagement.dto.EmployeeUpdateRequest;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.model.Employee;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.common.EmployeeValidator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final EmployeeValidator employeeValidator;
  private final MessageUtil messageUtil;
  private final PasswordEncoder passwordEncoder;
  private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
  private final TeamRepository teamRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
      EmployeeValidator employeeValidator, MessageUtil messageUtil,
      PasswordEncoder passwordEncoder, TeamRepository teamRepository) {
    this.employeeRepository = employeeRepository;
    this.employeeMapper = employeeMapper;
    this.employeeValidator = employeeValidator;
    this.messageUtil = messageUtil;
    this.passwordEncoder = passwordEncoder;
    this.teamRepository = teamRepository;
  }

  @Override
  public EmployeeResponse getEmployeeById(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Fetching employee with id: {}", id);

    Employee employee = retrieveEmployeeById(id);

    return employeeMapper.toEmployeeResponse(employee);
  }

  @Override
  public List<EmployeeResponse> getAllEmployees() {
    logger.debug("Fetching all employees");

    return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeResponse).toList();
  }

  @Override
  public UUID createEmployee(EmployeeInsertRequest employeeInsertRequest) {
    Objects.requireNonNull(employeeInsertRequest);
    logger.debug("Creating a new employee");

    employeeValidator.validateCreation(employeeInsertRequest);

    Employee employee = employeeMapper.toEmployee(employeeInsertRequest);
    employee.setPassword(passwordEncoder.encode(employeeInsertRequest.password()));
    employee.setIsEnabled(true);

    return employeeRepository.save(employee).getId();
  }

  @Override
  public void updateEmployee(UUID id, EmployeeUpdateRequest employeeUpdateRequest) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(employeeUpdateRequest);
    logger.debug("Updating employee with id: {}", id);

    Employee employee = retrieveEmployeeById(id);
    employeeValidator.validateUpdate(employeeUpdateRequest, employee);

    employee = employeeMapper.toEmployee(employeeUpdateRequest, employee);

    employeeRepository.save(employee);
  }

  @Override
  @Transactional
  public void deleteEmployee(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Deleting employee with id: {}", id);

    employeeValidator.validateDeletion(id);
    unsetTeamLeadIfEmployeeIsLead(id);

    employeeRepository.deleteById(id);
  }

  private Employee retrieveEmployeeById(UUID id) {
    return employeeRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("employee.not_found", id)
        ));
  }

  private void unsetTeamLeadIfEmployeeIsLead(UUID employeeId) {
    teamRepository.findByLead_Id(employeeId).ifPresent(team -> {
      team.setLead(null);
      teamRepository.save(team);
    });
  }
}
