package com.employeemanagement.repository;

import com.employeemanagement.model.Leave;
import jakarta.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {

  @Query("select l from Leave l where l.id = ?1")
  @EntityGraph(value = "Leave.employee")
  Optional<Leave> findByIdWithEmployee(UUID id);

  @Query("select l from Leave l")
  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllWithEmployee(Pageable pageable);

  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllByEmployeeId(UUID employeeId, Pageable pageable);

  @EntityGraph(value = "Leave.employee")
  Page<Leave> findAllByEmployee_Team_Lead_Id(UUID id, Pageable pageable);

  @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId "
      + "AND (:leaveId IS NULL OR l.id <> :leaveId) "
      + "AND l.state <> 'DENIED' "
      + "AND (l.startDate <= :endDate AND l.endDate >= :startDate)")
  List<Leave> findOverlappingLeaves(
      @Param("employeeId") UUID employeeId,
      @Nullable @Param("leaveId") UUID leaveId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}
