package com.employeemanagement.service.impl;

import static com.employeemanagement.util.EmployeeMocks.EMPLOYEES;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_EMAIL;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_FIRST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_ID;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_LAST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_MIDDLE_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_PASSWORD;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_UNHASHED_PASSWORD;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_USERNAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_2;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_2_ID;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_3;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_ID;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_PASSWORD;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_5;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_INSERT_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSES;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_1;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_2;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_3;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_4;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_5;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_UPDATE_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.TEAM_1;
import static com.employeemanagement.util.EmployeeMocks.TEAM_1_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employeemanagement.dto.EmployeeResponse;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.mapper.EmployeeMapper;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Team;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.common.EmployeeValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class EmployeeServiceImplTest {

  EmployeeRepository employeeRepository;

  EmployeeMapper employeeMapper;

  EmployeeValidator employeeValidator;

  TeamRepository teamRepository;

  PasswordEncoder passwordEncoder;

  MessageUtil messageUtil;

  EmployeeServiceImpl employeeService;

  @BeforeEach
  void setUp() {
    employeeRepository = mock(EmployeeRepository.class);
    employeeMapper = mock(EmployeeMapper.class);
    employeeValidator = mock(EmployeeValidator.class);
    teamRepository = mock(TeamRepository.class);
    passwordEncoder = mock(PasswordEncoder.class);
    messageUtil = mock(MessageUtil.class);

    employeeService = new EmployeeServiceImpl(
        employeeRepository,
        employeeMapper,
        employeeValidator,
        messageUtil,
        passwordEncoder,
        teamRepository
    );
  }

  @Test
  void givenValidEmployeeId_whenGetEmployeeById_thenReturnEmployeeResponse() {
    when(employeeRepository.findById(EMPLOYEE_1_ID)).thenReturn(Optional.of(EMPLOYEE_1));
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_1)).thenReturn(EMPLOYEE_RESPONSE_1);

    EmployeeResponse result = employeeService.getEmployeeById(EMPLOYEE_1_ID);

    assertEquals(EMPLOYEE_RESPONSE_1, result);
  }

  @Test
  void givenInvalidEmployeeId_whenGetEmployeeById_thenThrowNotFoundException() {
    UUID invalidEmployeeId = UUID.fromString("3b9e314c-3f8e-41ab-84c2-6d327a81aa84");
    String expectedExceptionMessage = "Employee not found";

    when(employeeRepository.findById(invalidEmployeeId)).thenReturn(Optional.empty());
    when(messageUtil.getMessage(any(), eq(invalidEmployeeId))).thenReturn(expectedExceptionMessage);

    NotFoundException notFoundException = assertThrows(NotFoundException.class,
        () -> employeeService.getEmployeeById(invalidEmployeeId));

    assertEquals(expectedExceptionMessage, notFoundException.getMessage());
  }

  @Test
  void givenNullId_whenGetEmployeeById_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeService.getEmployeeById(null));
  }

  @Test
  void givenEmployees_whenGetAllEmployees_thenReturnEmployeeResponseList() {
    when(employeeRepository.findAll()).thenReturn(EMPLOYEES);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_1)).thenReturn(EMPLOYEE_RESPONSE_1);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_2)).thenReturn(EMPLOYEE_RESPONSE_2);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_3)).thenReturn(EMPLOYEE_RESPONSE_3);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_4)).thenReturn(EMPLOYEE_RESPONSE_4);
    when(employeeMapper.toEmployeeResponse(EMPLOYEE_5)).thenReturn(EMPLOYEE_RESPONSE_5);

    List<EmployeeResponse> result = employeeService.getAllEmployees();

    assertEquals(EMPLOYEE_RESPONSES, result);
  }

  @Test
  void givenEmptyRepository_whenGetAllEmployees_thenReturnEmptyList() {
    when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

    List<EmployeeResponse> result = employeeService.getAllEmployees();

    assertEquals(new ArrayList<>(), result);
  }

  @Test
  void givenAnyEmployeeInsertRequest_whenCreateEmployee_thenValidateEmployeeInsertRequest() {
    when(employeeMapper.toEmployee(EMPLOYEE_INSERT_REQUEST)).thenReturn(EMPLOYEE_1);
    when(employeeRepository.save(EMPLOYEE_1)).thenReturn(EMPLOYEE_1);

    employeeService.createEmployee(EMPLOYEE_INSERT_REQUEST);

    verify(employeeValidator, times(1)).validateCreation(EMPLOYEE_INSERT_REQUEST);
  }

  @Test
  void givenValidEmployeeInsertRequest_whenCreateEmployee_thenSaveEmployeeAndReturnsUUID() {
    Employee employee = new Employee(
        EMPLOYEE_1.getFirstName(),
        EMPLOYEE_1.getMiddleName(),
        EMPLOYEE_1.getLastName(),
        EMPLOYEE_1.getUsername(),
        EMPLOYEE_1_UNHASHED_PASSWORD,
        EMPLOYEE_1.getEmail(),
        EMPLOYEE_1.getTeam()
    );

    when(employeeMapper.toEmployee(EMPLOYEE_INSERT_REQUEST)).thenReturn(employee);
    when(passwordEncoder.encode(EMPLOYEE_1_UNHASHED_PASSWORD)).thenReturn(EMPLOYEE_1_PASSWORD);
    when(employeeRepository.save(employee)).thenReturn(EMPLOYEE_1);

    UUID result = employeeService.createEmployee(EMPLOYEE_INSERT_REQUEST);

    assertTrue(employee.getIsEnabled());
    assertEquals(EMPLOYEE_1_PASSWORD, employee.getPassword());
    verify(employeeRepository, times(1)).save(employee);
    assertEquals(EMPLOYEE_1_ID, result);
  }

  @Test
  void givenNullEmployeeInsertRequest_whenCreateEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeService.createEmployee(null));
  }

  @Test
  void givenAnyEmployeeUpdateRequestAndId_whenUpdateEmployee_thenValidateEmployeeUpdateRequest() {
    when(employeeRepository.findById(EMPLOYEE_1_ID)).thenReturn(Optional.of(EMPLOYEE_1));
    when(employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_1)).thenReturn(EMPLOYEE_1);
    when(employeeRepository.save(EMPLOYEE_1)).thenReturn(EMPLOYEE_1);

    employeeService.updateEmployee(EMPLOYEE_1_ID, EMPLOYEE_UPDATE_REQUEST);

    verify(employeeValidator, times(1))
        .validateUpdate(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_1);
  }

  @Test
  void givenInvalidEmployeeId_whenUpdateEmployee_thenThrowNotFoundException() {
    UUID invalidEmployeeId = UUID.fromString("3b9e314c-3f8e-41ab-84c2-6d327a81aa84");
    String expectedExceptionMessage = "Employee not found";

    when(employeeRepository.findById(invalidEmployeeId)).thenReturn(Optional.empty());
    when(messageUtil.getMessage(any(), eq(invalidEmployeeId))).thenReturn(expectedExceptionMessage);

    NotFoundException notFoundException = assertThrows(NotFoundException.class, () ->
        employeeService.updateEmployee(invalidEmployeeId, EMPLOYEE_UPDATE_REQUEST));

    assertEquals(expectedExceptionMessage, notFoundException.getMessage());
  }

  @Test
  void givenValidEmployeeUpdateRequestAndId_whenUpdateEmployee_thenUpdateEmployee() {
    Employee employee = new Employee(
        EMPLOYEE_1_FIRST_NAME,
        EMPLOYEE_1_MIDDLE_NAME,
        EMPLOYEE_1_LAST_NAME,
        EMPLOYEE_1_USERNAME,
        EMPLOYEE_4_PASSWORD,
        EMPLOYEE_1_EMAIL,
        TEAM_1
    );

    when(employeeRepository.findById(EMPLOYEE_4_ID)).thenReturn(Optional.of(EMPLOYEE_4));
    when(employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, EMPLOYEE_4)).thenReturn(employee);
    when(employeeRepository.save(employee)).thenReturn(employee);

    employeeService.updateEmployee(EMPLOYEE_4_ID, EMPLOYEE_UPDATE_REQUEST);

    verify(employeeRepository, times(1)).save(employee);
  }

  @Test
  void givenNullEmployeeUpdateRequest_whenUpdateEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> employeeService.updateEmployee(EMPLOYEE_1_ID, null));
  }

  @Test
  void givenNullId_whenUpdateEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> employeeService.updateEmployee(null, EMPLOYEE_UPDATE_REQUEST));
  }

  @Test
  void givenAnyEmployeeId_whenDeleteEmployee_thenValidateEmployeeDeletion() {
    employeeService.deleteEmployee(EMPLOYEE_1_ID);

    verify(employeeValidator, times(1)).validateDeletion(EMPLOYEE_1_ID);
  }

  @Test
  void givenValidEmployeeId_whenDeleteEmployee_thenDeleteEmployee() {
    when(teamRepository.findByLead_Id(EMPLOYEE_2_ID)).thenReturn(Optional.empty());

    employeeService.deleteEmployee(EMPLOYEE_2_ID);

    verify(teamRepository, times(1)).findByLead_Id(EMPLOYEE_2_ID);
    verify(teamRepository, times(0)).save(any());
    verify(employeeRepository, times(1)).deleteById(EMPLOYEE_2_ID);
  }

  @Test
  void givenValidTeamLeadId_whenDeleteEmployee_thenDeleteEmployeeAndUnsetAsTheTeamLead() {
    Team team = new Team(TEAM_1_NAME, EMPLOYEE_1);

    when(teamRepository.findByLead_Id(EMPLOYEE_1_ID)).thenReturn(Optional.of(team));

    employeeService.deleteEmployee(EMPLOYEE_1_ID);

    verify(teamRepository, times(1)).findByLead_Id(EMPLOYEE_1_ID);
    verify(employeeRepository, times(1)).deleteById(EMPLOYEE_1_ID);
    assertNull(team.getLead());
  }

  @Test
  void givenNullId_whenDeleteEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeService.deleteEmployee(null));
  }
}