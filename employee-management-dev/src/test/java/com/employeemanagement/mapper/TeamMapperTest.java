package com.employeemanagement.mapper;

import static com.employeemanagement.util.TeamMocks.EMPLOYEE_2;
import static com.employeemanagement.util.TeamMocks.TEAM_1;
import static com.employeemanagement.util.TeamMocks.TEAM_2_NAME;
import static com.employeemanagement.util.TeamMocks.TEAM_3;
import static com.employeemanagement.util.TeamMocks.TEAM_REQUEST_1;
import static com.employeemanagement.util.TeamMocks.TEAM_REQUEST_3;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.model.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamMapperTest {

  @InjectMocks
  TeamMapper teamMapper;

  @Test
  void givenValidTeam_whenToTeamResponse_thenReturnTeamResponse() {
    TeamResponse result = teamMapper.toTeamResponse(TEAM_1);

    assertEquals(TEAM_RESPONSE_1, result);
  }

  @Test
  void givenNullTeam_whenToTeamResponse_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamMapper.toTeamResponse(null));
  }

  @Test
  void givenValidTeamRequest_whenToTeam_thenReturnTeam() {
    Team result = teamMapper.toTeam(TEAM_REQUEST_1);

    assertEquals(TEAM_1.getName(), result.getName());
    assertEquals(TEAM_1.getLead().getId(), result.getLead().getId());
  }

  @Test
  void givenValidTeamRequestWithoutLead_whenToTeam_thenReturnTeam() {
    Team result = teamMapper.toTeam(TEAM_REQUEST_3);

    assertEquals(TEAM_3.getName(), result.getName());
    assertNull(result.getLead());
  }

  @Test
  void givenNullTeamRequest_whenToTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamMapper.toTeam(null));
  }

  @Test
  void givenValidTeamRequestAndTeam_whenToTeam_thenReturnUpdatedTeam() {
    Team team = new Team(
        TEAM_2_NAME,
        EMPLOYEE_2
    );

    Team result = teamMapper.toTeam(TEAM_REQUEST_1, team);

    assertEquals(TEAM_1.getName(), result.getName());
    assertEquals(TEAM_1.getLead().getId(), result.getLead().getId());

    assertEquals(TEAM_1.getName(), team.getName());
    assertEquals(TEAM_1.getLead().getId(), team.getLead().getId());
  }

  @Test
  void givenValidTeamRequestWithoutLeadAndTeam_whenToTeam_thenReturnUpdatedTeam() {
    Team team = new Team(
        TEAM_2_NAME,
        EMPLOYEE_2
    );

    Team result = teamMapper.toTeam(TEAM_REQUEST_3, team);

    assertEquals(TEAM_3.getName(), result.getName());
    assertNull(result.getLead());

    assertEquals(TEAM_3.getName(), team.getName());
    assertNull(team.getLead());
  }

  @Test
  void givenNullTeamRequest_whenToTeamWithExistingTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamMapper.toTeam(null, TEAM_1));
  }

  @Test
  void givenNullTeam_whenToTeamWithTeamRequest_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamMapper.toTeam(TEAM_REQUEST_1, null));
  }
}