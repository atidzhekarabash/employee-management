package com.employeemanagement.validator.service.impl;

import static com.employeemanagement.util.TeamMocks.EMPLOYEE_1;
import static com.employeemanagement.util.TeamMocks.EMPLOYEE_1_ID;
import static com.employeemanagement.util.TeamMocks.TEAM_1;
import static com.employeemanagement.util.TeamMocks.TEAM_1_ID;
import static com.employeemanagement.util.TeamMocks.TEAM_1_NAME;
import static com.employeemanagement.util.TeamMocks.TEAM_2;
import static com.employeemanagement.util.TeamMocks.TEAM_2_NAME;
import static com.employeemanagement.util.TeamMocks.TEAM_3;
import static com.employeemanagement.util.TeamMocks.TEAM_3_NAME;
import static com.employeemanagement.util.TeamMocks.TEAM_4;
import static com.employeemanagement.util.TeamMocks.TEAM_REQUEST_1;
import static com.employeemanagement.util.TeamMocks.TEAM_REQUEST_3;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeemanagement.exception.common.DuplicateEntryException;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.employeemanagement.exception.common.TeamNotEmptyException;
import com.employeemanagement.model.Team;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamValidatorImplTest {

  @Mock
  TeamRepository teamRepository;

  @Mock
  EmployeeRepository employeeRepository;

  @Mock
  MessageUtil messageUtil;

  @InjectMocks
  TeamValidatorImpl teamValidator;

  @Test
  void givenValidTeamRequestWithLead_whenValidateCreation_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);
    when(teamRepository.existsByLead_Id(EMPLOYEE_1_ID)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateCreation(TEAM_REQUEST_1));
  }

  @Test
  void givenValidTeamRequestWithoutLead_whenValidateCreation_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_3_NAME)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateCreation(TEAM_REQUEST_3));
  }

  @Test
  void givenDuplicateTeamName_whenValidateCreation_thenThrowDuplicateEntryException() {
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(true);

    assertThrows(DuplicateEntryException.class,
        () -> teamValidator.validateCreation(TEAM_REQUEST_1));
  }

  @Test
  void givenNonExistentTeamLead_whenValidateCreation_thenThrowRelatedEntityNotFoundException() {
    String expectedMessage = "Lead does not exist";
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(false);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_ID))).thenReturn(expectedMessage);

    RelatedEntityNotFoundException result = assertThrows(RelatedEntityNotFoundException.class,
        () -> teamValidator.validateCreation(TEAM_REQUEST_1));

    assertEquals(expectedMessage, result.getMessage());
  }

  @Test
  void givenDuplicateTeamLead_whenValidateCreation_thenThrowDuplicateEntryException() {
    String expectedMessage = "Duplicate team lead";
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);
    when(teamRepository.existsByLead_Id(EMPLOYEE_1_ID)).thenReturn(true);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_ID))).thenReturn(expectedMessage);

    DuplicateEntryException result = assertThrows(DuplicateEntryException.class,
        () -> teamValidator.validateCreation(TEAM_REQUEST_1));

    assertEquals(expectedMessage, result.getMessage());
  }

  @Test
  void givenNullTeamRequest_whenValidateCreation_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamValidator.validateCreation(null));
  }
  
  @Test
  void givenValidTeamRequestWithDifferentLead_whenValidateUpdate_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);
    when(teamRepository.existsByLead_Id(EMPLOYEE_1_ID)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateUpdate(TEAM_REQUEST_1, TEAM_2));
  }

  @Test
  void givenValidTeamRequestWithSameLead_whenValidateUpdate_thenDoNotThrowException() {
    Team teamToUpdate = new Team(
        TEAM_2_NAME,
        EMPLOYEE_1
    );
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateUpdate(TEAM_REQUEST_1, teamToUpdate));

    verify(employeeRepository, times(0)).existsById(EMPLOYEE_1_ID);
    verify(teamRepository, times(0)).existsByLead_Id(EMPLOYEE_1_ID);
  }

  @Test
  void givenValidTeamRequestWithLeadAndTeamWithoutLead_whenValidateUpdate_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);
    when(teamRepository.existsByLead_Id(EMPLOYEE_1_ID)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateUpdate(TEAM_REQUEST_1, TEAM_3));
  }

  @Test
  void givenValidTeamRequestWithoutLeadAndTeamWithLead_whenValidateUpdate_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_3_NAME)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateUpdate(TEAM_REQUEST_3, TEAM_1));

    verify(employeeRepository, times(0)).existsById(EMPLOYEE_1_ID);
    verify(teamRepository, times(0)).existsByLead_Id(EMPLOYEE_1_ID);
  }

  @Test
  void givenValidTeamRequestWithoutLeadAndTeamWithoutLead_whenValidateUpdate_thenDoNotThrowException() {
    when(teamRepository.existsByName(TEAM_3_NAME)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateUpdate(TEAM_REQUEST_3, TEAM_4));

    verify(employeeRepository, times(0)).existsById(EMPLOYEE_1_ID);
    verify(teamRepository, times(0)).existsByLead_Id(EMPLOYEE_1_ID);
  }

  @Test
  void givenNonExistentTeamLead_whenValidateUpdate_thenThrowRelatedEntityNotFoundException() {
    String expectedMessage = "Lead does not exist";
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(false);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_ID))).thenReturn(expectedMessage);

    RelatedEntityNotFoundException result = assertThrows(RelatedEntityNotFoundException.class,
        () -> teamValidator.validateUpdate(TEAM_REQUEST_1, TEAM_2));

    assertEquals(expectedMessage, result.getMessage());
  }

  @Test
  void givenDuplicateTeamLead_whenValidateUpdate_thenThrowDuplicateEntryException() {
    String expectedMessage = "Duplicate team lead";
    when(teamRepository.existsByName(TEAM_1_NAME)).thenReturn(false);
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);
    when(teamRepository.existsByLead_Id(EMPLOYEE_1_ID)).thenReturn(true);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_ID))).thenReturn(expectedMessage);

    DuplicateEntryException result = assertThrows(DuplicateEntryException.class,
        () -> teamValidator.validateUpdate(TEAM_REQUEST_1, TEAM_3));

    assertEquals(expectedMessage, result.getMessage());
  }

  @Test
  void givenNullTeamRequest_whenValidateUpdate_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamValidator.validateUpdate(null, TEAM_1));
  }

  @Test
  void givenNullTeam_whenValidateUpdate_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamValidator.validateUpdate(TEAM_REQUEST_1, null));
  }

  @Test
  void givenValidId_whenValidateDelete_thenDoNotThrowException() {
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(teamRepository.hasEmployees(TEAM_1_ID)).thenReturn(false);

    assertDoesNotThrow(() -> teamValidator.validateDeletion(TEAM_1_ID));
  }

  @Test
  void givenNonExistentTeam_whenValidateDelete_thenThrowNotFoundException() {
    String expectedMessage = "Team not empty";
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(false);
    when(messageUtil.getMessage(any(), eq(TEAM_1_ID))).thenReturn(expectedMessage);

    NotFoundException teamNotEmptyException = assertThrows(NotFoundException.class,
        () -> teamValidator.validateDeletion(TEAM_1_ID));

    assertEquals(expectedMessage, teamNotEmptyException.getMessage());
  }

  @Test
  void givenNonEmptyTeam_whenValidateDelete_thenThrowTeamNotEmptyException() {
    String expectedMessage = "Team not empty";
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(teamRepository.hasEmployees(TEAM_1_ID)).thenReturn(true);
    when(messageUtil.getMessage(any(), eq(TEAM_1_ID))).thenReturn(expectedMessage);

    TeamNotEmptyException teamNotEmptyException = assertThrows(TeamNotEmptyException.class,
        () -> teamValidator.validateDeletion(TEAM_1_ID));

    assertEquals(expectedMessage, teamNotEmptyException.getMessage());
  }

  @Test
  void givenNullId_whenValidateDeletion_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> teamValidator.validateDeletion(null));
  }
}