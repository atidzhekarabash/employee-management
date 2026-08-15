package com.employeemanagement.repository;

import com.employeemanagement.model.Team;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, UUID> {

  boolean existsByName(String name);

  boolean existsByLead_Id(UUID id);

  Optional<Team> findByLead_Id(UUID id);

  @Query("select count(e) > 0 from Employee e where e.team.id = :id")
  boolean hasEmployees(@Param("id") UUID id);
}
