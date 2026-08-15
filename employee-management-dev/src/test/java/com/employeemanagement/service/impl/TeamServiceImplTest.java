package com.employeemanagement.service.impl;


import static com.employeemanagement.util.TeamMocks.EMPLOYEE_1;
import static com.employeemanagement.util.TeamMocks.TEAMS;
import static com.employeemanagement.util.TeamMocks.TEAM_1;
import static com.employeemanagement.util.TeamMocks.TEAM_1_ID;
import static com.employeemanagement.util.TeamMocks.TEAM_1_NAME;
import static com.employeemanagement.util.TeamMocks.TEAM_2;
import static com.employeemanagement.util.TeamMocks.TEAM_2_ID;
import static com.employeemanagement.util.TeamMocks.TEAM_3;
import static com.employeemanagement.util.TeamMocks.TEAM_4;
import static com.employeemanagement.util.TeamMocks.TEAM_4_ID;
import static com.employeemanagement.util.TeamMocks.TEAM_REQUEST_1;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSES;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSE_1;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSE_2;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSE_3;
import static com.employeemanagement.util.TeamMocks.TEAM_RESPONSE_4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.mapper.TeamMapper;
import com.employeemanagement.model.Team;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.TeamValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private TeamMapper teamMapper;

  @Mock
  private TeamValidator teamValidator;

  @Mock
  private MessageUtil messageUtil;

  @InjectMocks
  private TeamServiceImpl teamService;

  @Test
  void givenValidTeamId_whenGetTeamById_thenReturnTeamResponse() {
    when(teamRepository.findById(TEAM_1_ID)).thenReturn(Optional.of(TEAM_1));
    when(teamMapper.toTeamResponse(TEAM_1)).thenReturn(TEAM_RESPONSE_1);

    TeamResponse result = teamService.getTeamById(TEAM_1_ID);

    assertEquals(TEAM_RESPONSE_1, result);
  }

  @Test
  void givenInvalidTeamId_whenGetTeamById_thenThrowNotFoundException() {
    UUID invalidTeamId = UUID.fromString("7196288e-61e9-41ef-8338-733a9b30555d");
    String expectedExceptionMessage = "Team not found";

    when(teamRepository.findById(invalidTeamId)).thenReturn(Optional.empty());
    when(messageUtil.getMessage(any(), eq(invalidTeamId))).thenReturn(expectedExceptionMessage);

    NotFoundException notFoundException = assertThrows(NotFoundException.class,
        () -> teamService.getTeamById(invalidTeamId));

    assertEquals(expectedExceptionMessage, notFoundException.getMessage());
  }

  @Test
  void givenNullId_whenGetTeamById_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamService.getTeamById(null));
  }

  @Test
  void givenTeams_whenGetAllTeams_thenReturnTeamResponseList() {
    when(teamRepository.findAll()).thenReturn(TEAMS);
    when(teamMapper.toTeamResponse(TEAM_1)).thenReturn(TEAM_RESPONSE_1);
    when(teamMapper.toTeamResponse(TEAM_2)).thenReturn(TEAM_RESPONSE_2);
    when(teamMapper.toTeamResponse(TEAM_3)).thenReturn(TEAM_RESPONSE_3);
    when(teamMapper.toTeamResponse(TEAM_4)).thenReturn(TEAM_RESPONSE_4);

    List<TeamResponse> result = teamService.getAllTeams();

    assertEquals(TEAM_RESPONSES, result);
  }

  @Test
  void givenEmptyRepository_whenGetAllTeams_thenReturnEmptyList() {
    when(teamRepository.findAll()).thenReturn(new ArrayList<>());

    List<TeamResponse> result = teamService.getAllTeams();

    assertEquals(new ArrayList<>(), result);
  }

  @Test
  void givenAnyTeamRequest_whenCreateTeam_thenValidateTeamRequest() {
    when(teamMapper.toTeam(TEAM_REQUEST_1)).thenReturn(TEAM_1);
    when(teamRepository.save(TEAM_1)).thenReturn(TEAM_1);

    teamService.createTeam(TEAM_REQUEST_1);

    verify(teamValidator, times(1)).validateCreation(TEAM_REQUEST_1);
  }

  @Test
  void givenValidTeamRequest_whenCreateTeam_thenSaveTeamAndReturnsUUID() {
    Team team = new Team(
        TEAM_1_NAME,
        EMPLOYEE_1
    );

    when(teamMapper.toTeam(TEAM_REQUEST_1)).thenReturn(team);
    when(teamRepository.save(team)).thenReturn(TEAM_1);

    UUID result = teamService.createTeam(TEAM_REQUEST_1);

    verify(teamRepository, times(1)).save(team);
    assertEquals(TEAM_1_ID, result);
  }

  @Test
  void givenNullTeamRequest_whenCreateTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamService.createTeam(null));
  }

  @Test
  void givenAnyTeamRequestAndId_whenUpdateTeam_thenValidateTeamUpdateRequest() {
    when(teamRepository.findById(TEAM_1_ID)).thenReturn(Optional.of(TEAM_1));
    when(teamMapper.toTeam(TEAM_REQUEST_1, TEAM_1)).thenReturn(TEAM_1);
    when(teamRepository.save(TEAM_1)).thenReturn(TEAM_1);

    teamService.updateTeam(TEAM_1_ID, TEAM_REQUEST_1);

    verify(teamValidator, times(1)).validateUpdate(TEAM_REQUEST_1, TEAM_1);
  }

  @Test
  void givenInvalidTeamId_whenUpdateTeam_thenThrowNotFoundException() {
    UUID invalidTeamId = UUID.fromString("1bc4ae90-0e60-444a-bc27-01b23ed497d9");
    String expectedExceptionMessage = "Team not found";

    when(teamRepository.findById(invalidTeamId)).thenReturn(Optional.empty());
    when(messageUtil.getMessage(any(), eq(invalidTeamId))).thenReturn(expectedExceptionMessage);

    NotFoundException notFoundException = assertThrows(NotFoundException.class, () ->
        teamService.updateTeam(invalidTeamId, TEAM_REQUEST_1));

    assertEquals(expectedExceptionMessage, notFoundException.getMessage());
  }

  @Test
  void givenValidTeamRequestAndId_whenUpdateTeam_thenUpdateTeam() {
    when(teamRepository.findById(TEAM_4_ID)).thenReturn(Optional.of(TEAM_4));
    when(teamMapper.toTeam(TEAM_REQUEST_1, TEAM_4)).thenReturn(TEAM_1);
    when(teamRepository.save(TEAM_1)).thenReturn(TEAM_1);

    teamService.updateTeam(TEAM_4_ID, TEAM_REQUEST_1);

    verify(teamRepository, times(1)).save(TEAM_1);
  }

  @Test
  void givenNullTeamRequest_whenUpdateTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> teamService.updateTeam(TEAM_1_ID, null));
  }

  @Test
  void givenNullId_whenUpdateTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> teamService.updateTeam(null, TEAM_REQUEST_1));
  }

  @Test
  void givenAnyTeamId_whenDeleteTeam_thenValidateTeamDeletion() {
    teamService.deleteTeam(TEAM_1_ID);

    verify(teamValidator, times(1)).validateDeletion(TEAM_1_ID);
  }

  @Test
  void givenValidTeamId_whenDeleteTeam_thenDeleteTeam() {
    teamService.deleteTeam(TEAM_2_ID);
    
    verify(teamRepository, times(1)).deleteById(TEAM_2_ID);
  }

  @Test
  void givenNullId_whenDeleteTeam_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamService.deleteTeam(null));
  }
}
