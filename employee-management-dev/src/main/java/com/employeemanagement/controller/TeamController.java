package com.employeemanagement.controller;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.service.TeamService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/teams")
public class TeamController {

  private final TeamService teamService;

  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeamResponse> getTeamById(@PathVariable UUID id) {
    return ResponseEntity.ok(teamService.getTeamById(id));
  }

  @GetMapping
  public ResponseEntity<List<TeamResponse>> getAllTeams() {
    return ResponseEntity.ok(teamService.getAllTeams());
  }

  @PostMapping
  public ResponseEntity<Void> createTeam(@Valid @RequestBody TeamRequest teamRequest) {
    UUID teamId = teamService.createTeam(teamRequest);
    URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/teams/{id}")
        .buildAndExpand(teamId)
        .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateTeam(
      @PathVariable UUID id,
      @Valid @RequestBody TeamRequest teamRequest
  ) {
    teamService.updateTeam(id, teamRequest);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
    teamService.deleteTeam(id);

    return ResponseEntity.noContent().build();
  }
}
