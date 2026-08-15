package com.employeemanagement.validator.service.impl;

import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_EMAIL;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_FIRST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_ID;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_LAST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_MIDDLE_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_USERNAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_2;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_2_EMAIL;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_2_USERNAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_INSERT_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_UPDATE_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.TEAM_1_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeemanagement.dto.EmployeeUpdateRequest;
import com.employeemanagement.exception.common.DuplicateEntryException;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorImplTest {

  @Mock
  TeamRepository teamRepository;

  @Mock
  EmployeeRepository employeeRepository;

  @Mock
  MessageUtil messageUtil;

  @InjectMocks
  EmployeeValidatorImpl employeeValidator;

  @Test
  void givenValidEmployeeInsertRequest_whenValidateCreation_thenDoNotThrowException() {
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(employeeRepository.existsByUsername(EMPLOYEE_1_USERNAME)).thenReturn(false);
    when(employeeRepository.existsByEmail(EMPLOYEE_1_EMAIL)).thenReturn(false);

    assertDoesNotThrow(() -> employeeValidator.validateCreation(EMPLOYEE_INSERT_REQUEST));
  }

  @Test
  void givenNullEmployeeInsertRequest_whenValidateCreation_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeValidator.validateCreation(null));
  }

  @Test
  void givenNonExistentTeam_whenValidateCreation_thenDoThrowRelatedEntityNotFoundException() {
    String expectedExceptionMessage = "Team not found";
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(false);
    when(messageUtil.getMessage(any(), eq(TEAM_1_ID))).thenReturn(expectedExceptionMessage);

    RelatedEntityNotFoundException result = assertThrows(RelatedEntityNotFoundException.class,
        () -> employeeValidator.validateCreation(EMPLOYEE_INSERT_REQUEST));

    assertEquals(expectedExceptionMessage, result.getMessage());
  }

  @Test
  void givenDuplicateUsername_whenValidateCreation_thenDuplicateEntryException() {
    String expectedExceptionMessage = "Duplicate username";
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(employeeRepository.existsByUsername(EMPLOYEE_1_USERNAME)).thenReturn(true);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_USERNAME))).thenReturn(
        expectedExceptionMessage);

    DuplicateEntryException result = assertThrows(DuplicateEntryException.class,
        () -> employeeValidator.validateCreation(EMPLOYEE_INSERT_REQUEST));

    assertEquals(expectedExceptionMessage, result.getMessage());
  }

  @Test
  void givenDuplicateEmail_whenValidateCreation_thenDuplicateEntryException() {
    String expectedExceptionMessage = "Duplicate email";
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(employeeRepository.existsByUsername(EMPLOYEE_1_USERNAME)).thenReturn(false);
    when(employeeRepository.existsByEmail(EMPLOYEE_1_EMAIL)).thenReturn(true);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_EMAIL))).thenReturn(expectedExceptionMessage);

    DuplicateEntryException result = assertThrows(DuplicateEntryException.class,
        () -> employeeValidator.validateCreation(EMPLOYEE_INSERT_REQUEST));

    assertEquals(expectedExceptionMessage, result.getMessage());
  }

  @Test
  void givenNullEmployeeUpdateRequest_whenValidateUpdate_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> employeeValidator.validateUpdate(null, EMPLOYEE_1));
  }

  @Test
  void givenNullEmployeeEntity_whenValidateUpdate_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> employeeValidator.validateUpdate(EMPLOYEE_UPDATE_REQUEST, null));
  }

  @Test
  void givenValidEmployeeUpdateRequest_whenValidateUpdate_thenDoNotThrowException() {
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);
    when(employeeRepository.existsByUsername(EMPLOYEE_1_USERNAME)).thenReturn(false);
    when(employeeRepository.existsByEmail(EMPLOYEE_1_EMAIL)).thenReturn(false);

    assertDoesNotThrow(() -> employeeValidator.validateUpdate(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_2));

    verify(employeeRepository, times(1)).existsByUsername(EMPLOYEE_1_USERNAME);
    verify(employeeRepository, times(1)).existsByEmail(EMPLOYEE_1_EMAIL);
  }

  @Test
  void givenUnchangedUsernameAndEmail_whenValidateUpdate_thenSkipDuplicateCheck() {
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);

    assertDoesNotThrow(() -> employeeValidator.validateUpdate(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_1));

    verify(employeeRepository, times(0)).existsByUsername(EMPLOYEE_1_USERNAME);
    verify(employeeRepository, times(0)).existsByEmail(EMPLOYEE_1_EMAIL);
  }

  @Test
  void givenValidChangedUsername_whenValidateUpdate_thenCheckIfDuplicateUsername() {
    when(employeeRepository.existsByUsername(EMPLOYEE_2_USERNAME)).thenReturn(false);
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);

    EmployeeUpdateRequest requestWithNewUsername = getEmployeeUpdateRequest(EMPLOYEE_2_USERNAME,
        EMPLOYEE_1_EMAIL);

    assertDoesNotThrow(() -> employeeValidator.validateUpdate(requestWithNewUsername, EMPLOYEE_1));

    verify(employeeRepository, times(1)).existsByUsername(EMPLOYEE_2_USERNAME);
  }

  @Test
  void givenValidChangedEmailAndUnchangedUsername_whenValidateUpdate_thenCheckIfDuplicateEmail() {
    when(employeeRepository.existsByEmail(EMPLOYEE_2_EMAIL)).thenReturn(false);
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);

    EmployeeUpdateRequest requestWithNewEmail = getEmployeeUpdateRequest(EMPLOYEE_1_USERNAME,
        EMPLOYEE_2_EMAIL);

    assertDoesNotThrow(() -> employeeValidator.validateUpdate(requestWithNewEmail, EMPLOYEE_1));

    verify(employeeRepository, times(1)).existsByEmail(EMPLOYEE_2_EMAIL);
  }

  @Test
  void givenUnchangedEmail_whenValidateUpdate_thenEmailNotChecked() {
    when(employeeRepository.existsByUsername(EMPLOYEE_2_USERNAME)).thenReturn(false);
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);

    EmployeeUpdateRequest requestWithUnchangedEmail = getEmployeeUpdateRequest(EMPLOYEE_2_USERNAME,
        EMPLOYEE_1_EMAIL);

    assertDoesNotThrow(
        () -> employeeValidator.validateUpdate(requestWithUnchangedEmail, EMPLOYEE_1));

    verify(employeeRepository, times(0)).existsByEmail(EMPLOYEE_1_EMAIL);
  }

  @Test
  void givenUnchangedUsername_whenValidateUpdate_thenUsernameNotChecked() {
    when(employeeRepository.existsByEmail(EMPLOYEE_2_EMAIL)).thenReturn(false);
    when(teamRepository.existsById(TEAM_1_ID)).thenReturn(true);

    EmployeeUpdateRequest requestWithUnchangedUsername = getEmployeeUpdateRequest(
        EMPLOYEE_1_USERNAME,
        EMPLOYEE_2_EMAIL);

    assertDoesNotThrow(
        () -> employeeValidator.validateUpdate(requestWithUnchangedUsername, EMPLOYEE_1));

    verify(employeeRepository, times(0)).existsByUsername(EMPLOYEE_1_USERNAME);
  }

  @Test
  void givenChangedDuplicateUsername_whenValidateUpdate_thenThrowDuplicateEntityNotFoundException() {
    when(employeeRepository.existsByUsername(EMPLOYEE_2_USERNAME)).thenReturn(true);

    EmployeeUpdateRequest requestWithNewUsername = getEmployeeUpdateRequest(EMPLOYEE_2_USERNAME,
        EMPLOYEE_1_EMAIL);

    assertThrows(DuplicateEntryException.class,
        () -> employeeValidator.validateUpdate(requestWithNewUsername, EMPLOYEE_1));
  }

  @Test
  void givenChangedDuplicateEmail_whenValidateUpdate_thenThrowDuplicateEntityNotFoundException() {
    when(employeeRepository.existsByEmail(EMPLOYEE_2_EMAIL)).thenReturn(true);

    EmployeeUpdateRequest requestWithNewEmail = getEmployeeUpdateRequest(EMPLOYEE_1_USERNAME,
        EMPLOYEE_2_EMAIL);

    assertThrows(DuplicateEntryException.class,
        () -> employeeValidator.validateUpdate(requestWithNewEmail, EMPLOYEE_1));
  }

  @Test
  void givenNullEmployeeId_whenValidateValidateDeletion_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeValidator.validateDeletion(null));
  }

  @Test
  void givenValidEmployeeId_whenValidateDeletion_thenDoNotThrowException() {
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(true);

    assertDoesNotThrow(() -> employeeValidator.validateDeletion(EMPLOYEE_1_ID));
  }

  @Test
  void givenMissingEmployeeId_whenValidateDeletion_thenThrowNotFoundException() {
    String expectedExceptionMessage = "Employee not found";
    when(employeeRepository.existsById(EMPLOYEE_1_ID)).thenReturn(false);
    when(messageUtil.getMessage(any(), eq(EMPLOYEE_1_ID))).thenReturn(expectedExceptionMessage);

    NotFoundException notFoundException = assertThrows(NotFoundException.class,
        () -> employeeValidator.validateDeletion(EMPLOYEE_1_ID));

    assertEquals(expectedExceptionMessage, notFoundException.getMessage());
  }

  private static EmployeeUpdateRequest getEmployeeUpdateRequest(String username, String email) {
    return new EmployeeUpdateRequest(
        EMPLOYEE_1_FIRST_NAME,
        EMPLOYEE_1_MIDDLE_NAME,
        EMPLOYEE_1_LAST_NAME,
        username,
        email,
        TEAM_1_ID
    );
  }
}