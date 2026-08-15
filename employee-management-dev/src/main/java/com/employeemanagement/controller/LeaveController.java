package com.employeemanagement.controller;

import com.employeemanagement.dto.LeaveInsertRequest;
import com.employeemanagement.dto.LeaveResponse;
import com.employeemanagement.dto.LeaveUpdateRequest;
import com.employeemanagement.service.LeaveService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class LeaveController {

  private final LeaveService leaveService;

  public LeaveController(LeaveService leaveService) {
    this.leaveService = leaveService;
  }

  @GetMapping("/leaves/{id}")
  public ResponseEntity<LeaveResponse> getLeaveById(@PathVariable UUID id) {
    return ResponseEntity.ok(leaveService.getLeaveById(id));
  }

  @GetMapping("/leaves")
  public ResponseEntity<Page<LeaveResponse>> getAllLeaves(Pageable pageable) {
    return ResponseEntity.ok(leaveService.getAllLeaves(pageable));
  }

  @GetMapping("/employee/{employeeId}/leaves")
  public ResponseEntity<Page<LeaveResponse>> getAllLeavesByEmployeeId(
      @PathVariable UUID employeeId, Pageable pageable) {
    return ResponseEntity.ok(leaveService.getAllLeavesByEmployeeId(employeeId, pageable));
  }

  @GetMapping("/lead/{leadId}/leaves")
  public ResponseEntity<Page<LeaveResponse>> getAllLeavesByLeadId(
      @PathVariable UUID leadId, Pageable pageable) {
    return ResponseEntity.ok(leaveService.getAllLeavesByLeadId(leadId, pageable));
  }

  @PostMapping("/leaves")
  public ResponseEntity<Void> createLeave(
      @Valid @RequestBody LeaveInsertRequest leaveInsertRequest) {
    UUID leaveId = leaveService.createLeave(leaveInsertRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(leaveId)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/leaves/{id}")
  public ResponseEntity<Void> updateLeave(
      @PathVariable UUID id,
      @Valid @RequestBody LeaveUpdateRequest leaveUpdateRequest
  ) {
    leaveService.updateLeave(id, leaveUpdateRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/leaves/{id}")
  public ResponseEntity<Void> deleteLeave(@PathVariable UUID id) {
    leaveService.deleteLeave(id);

    return ResponseEntity.noContent().build();
  }
}