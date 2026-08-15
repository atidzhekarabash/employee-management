package com.employeemanagement.service.impl;

import com.employeemanagement.dto.LeaveInsertRequest;
import com.employeemanagement.dto.LeaveResponse;
import com.employeemanagement.dto.LeaveUpdateRequest;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.mapper.LeaveMapper;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Leave;
import com.employeemanagement.model.common.LeaveState;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.LeaveRepository;
import com.employeemanagement.service.LeaveService;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.LeaveValidator;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

  private final LeaveRepository leaveRepository;
  private final LeaveMapper leaveMapper;
  private final LeaveValidator leaveValidator;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
  private final EmployeeRepository employeeRepository;

  public LeaveServiceImpl(LeaveRepository leaveRepository, LeaveMapper leaveMapper,
      LeaveValidator leaveValidator,
      MessageUtil messageUtil,
      EmployeeRepository employeeRepository) {
    this.leaveRepository = leaveRepository;
    this.leaveMapper = leaveMapper;
    this.leaveValidator = leaveValidator;
    this.messageUtil = messageUtil;
    this.employeeRepository = employeeRepository;
  }

  @Override
  public LeaveResponse getLeaveById(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Fetching leave with id: {}", id);

    return leaveMapper.toLeaveResponse(retrieveLeaveById(id, true));
  }

  @Override
  public Page<LeaveResponse> getAllLeaves(Pageable pageable) {
    Objects.requireNonNull(pageable);
    logger.debug("Fetching all leaves");

    return leaveRepository.findAllWithEmployee(pageable).map(leaveMapper::toLeaveResponse);
  }

  @Override
  public Page<LeaveResponse> getAllLeavesByEmployeeId(UUID employeeId, Pageable pageable) {
    Objects.requireNonNull(employeeId);
    Objects.requireNonNull(pageable);
    logger.debug("Fetching all leaves by employee: {}", employeeId);

    return leaveRepository.findAllByEmployeeId(employeeId, pageable)
        .map(leaveMapper::toLeaveResponse);
  }

  @Override
  public Page<LeaveResponse> getAllLeavesByLeadId(UUID leadId, Pageable pageable) {
    Objects.requireNonNull(leadId);
    Objects.requireNonNull(pageable);
    logger.debug("Fetching all leaves for lead: {}", leadId);

    return leaveRepository.findAllByEmployee_Team_Lead_Id(leadId, pageable)
        .map(leaveMapper::toLeaveResponse);
  }

  @Override
  public UUID createLeave(LeaveInsertRequest leaveInsertRequest) {
    Objects.requireNonNull(leaveInsertRequest);
    logger.debug("Creating a new leave");

    leaveValidator.validateCreation(leaveInsertRequest);
    Employee employee = employeeRepository.getReferenceById(leaveInsertRequest.employeeId());

    Leave leave = leaveMapper.toLeave(leaveInsertRequest, LocalDate.now(), LeaveState.PENDING,
        employee);

    return leaveRepository.save(leave).getId();
  }

  @Override
  public void updateLeave(UUID id, LeaveUpdateRequest leaveUpdateRequest) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(leaveUpdateRequest);
    logger.debug("Updating leave with id: {}", id);

    Leave leave = retrieveLeaveById(id, false);
    leaveValidator.validateUpdate(leaveUpdateRequest, leave);

    leaveRepository.save(leaveMapper.toLeave(leaveUpdateRequest, leave));
  }

  @Override
  public void deleteLeave(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Deleting leave with id: {}", id);

    leaveValidator.validateDeletion(id);
    leaveRepository.deleteById(id);
  }

  private Leave retrieveLeaveById(UUID id, boolean fetchEmployee) {
    Optional<Leave> leaveOptional;

    if (fetchEmployee) {
      leaveOptional = leaveRepository.findByIdWithEmployee(id);
    } else {
      leaveOptional = leaveRepository.findById(id);
    }

    return leaveOptional.orElseThrow(() -> new NotFoundException(
        messageUtil.getMessage("leave.not_found", id)
    ));
  }
}
