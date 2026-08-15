package com.employeemanagement.mapper;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Team;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

  public TeamResponse toTeamResponse(Team team) {
    Objects.requireNonNull(team);

    UUID leadId = Objects.nonNull(team.getLead()) ? team.getLead().getId() : null;

    return new TeamResponse(team.getId(), team.getName(), leadId);
  }

  public Team toTeam(TeamRequest teamRequest) {
    Objects.requireNonNull(teamRequest);

    Team team = new Team();
    team.setName(teamRequest.name());

    setEmployee(teamRequest, team);

    return team;
  }

  public Team toTeam(TeamRequest teamRequest, Team team) {
    Objects.requireNonNull(teamRequest);
    Objects.requireNonNull(team);

    team.setName(teamRequest.name());

    setEmployee(teamRequest, team);

    return team;
  }

  private static void setEmployee(TeamRequest teamRequest, Team team) {
    if (teamRequest.leadId() == null) {
      team.setLead(null);
      return;
    }

    Employee employee = new Employee();
    employee.setId(teamRequest.leadId());

    team.setLead(employee);
  }
}
