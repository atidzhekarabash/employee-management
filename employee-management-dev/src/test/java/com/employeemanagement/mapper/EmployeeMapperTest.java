package com.employeemanagement.mapper;

import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_EMAIL;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_FIRST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_LAST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_MIDDLE_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_UNHASHED_PASSWORD;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_1_USERNAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_EMAIL;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_FIRST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_LAST_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_MIDDLE_NAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_PASSWORD;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_4_USERNAME;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_INSERT_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_RESPONSE_1;
import static com.employeemanagement.util.EmployeeMocks.EMPLOYEE_UPDATE_REQUEST;
import static com.employeemanagement.util.EmployeeMocks.TEAM_1;
import static com.employeemanagement.util.EmployeeMocks.TEAM_1_ID;
import static com.employeemanagement.util.EmployeeMocks.TEAM_2;
import static com.employeemanagement.util.EmployeeMocks.TEAM_RESPONSE_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.employeemanagement.dto.EmployeeResponse;
import com.employeemanagement.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

  @Mock
  TeamMapper teamMapper;

  @InjectMocks
  EmployeeMapper employeeMapper;

  @Test
  void givenValidEmployee_whenToEmployeeResponse_thenReturnEmployeeResponse() {
    when(teamMapper.toTeamResponse(TEAM_1)).thenReturn(TEAM_RESPONSE_1);

    EmployeeResponse result = employeeMapper.toEmployeeResponse(EMPLOYEE_1);

    assertEquals(EMPLOYEE_RESPONSE_1, result);
  }

  @Test
  void givenNullEmployee_whenToEmployeeResponse_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> employeeMapper.toEmployeeResponse(null));
  }

  @Test
  void givenValidEmployeeInsertRequest_whenToEmployee_thenReturnEmployee() {
    Employee result = employeeMapper.toEmployee(EMPLOYEE_INSERT_REQUEST);

    assertEquals(EMPLOYEE_1_FIRST_NAME, result.getFirstName());
    assertEquals(EMPLOYEE_1_MIDDLE_NAME, result.getMiddleName());
    assertEquals(EMPLOYEE_1_LAST_NAME, result.getLastName());
    assertEquals(EMPLOYEE_1_USERNAME, result.getUsername());
    assertEquals(EMPLOYEE_1_UNHASHED_PASSWORD, result.getPassword());
    assertEquals(EMPLOYEE_1_EMAIL, result.getEmail());
    assertEquals(TEAM_1_ID, result.getTeam().getId());
  }

  @Test
  void givenNullEmployeeInsertRequest_whenToEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class,
        () -> employeeMapper.toEmployee(null));
  }

  @Test
  void givenValidEmployeeUpdateRequest_whenToEmployee_thenReturnEmployee() {
    Employee employee = getEmployeeForUpdate();

    Employee result = employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, employee);

    assertEquals(EMPLOYEE_1_FIRST_NAME, result.getFirstName());
    assertEquals(EMPLOYEE_1_MIDDLE_NAME, result.getMiddleName());
    assertEquals(EMPLOYEE_1_LAST_NAME, result.getLastName());
    assertEquals(EMPLOYEE_1_USERNAME, result.getUsername());
    assertEquals(EMPLOYEE_1_EMAIL, result.getEmail());
    assertEquals(TEAM_1.getId(), result.getTeam().getId());

    assertEquals(EMPLOYEE_1_FIRST_NAME, employee.getFirstName());
    assertEquals(EMPLOYEE_1_MIDDLE_NAME, employee.getMiddleName());
    assertEquals(EMPLOYEE_1_LAST_NAME, employee.getLastName());
    assertEquals(EMPLOYEE_1_USERNAME, employee.getUsername());
    assertEquals(EMPLOYEE_1_EMAIL, employee.getEmail());
    assertEquals(TEAM_1.getId(), employee.getTeam().getId());
  }

  @Test
  void givenValidEmployeeUpdateRequest_whenToEmployee_thenPasswordShouldNotChange() {
    Employee employee = getEmployeeForUpdate();

    Employee result = employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, employee);

    assertEquals(EMPLOYEE_4_PASSWORD, result.getPassword());
    assertEquals(EMPLOYEE_4_PASSWORD, employee.getPassword());
  }

  @Test
  void givenNullEmployeeUpdateRequest_whenToEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class
        , () -> employeeMapper.toEmployee(null, EMPLOYEE_1));
  }

  @Test
  void givenNullEmployee_whenToEmployee_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class
        , () -> employeeMapper.toEmployee(EMPLOYEE_UPDATE_REQUEST, null));
  }

  private static Employee getEmployeeForUpdate() {
    return new Employee(
        EMPLOYEE_4_FIRST_NAME,
        EMPLOYEE_4_MIDDLE_NAME,
        EMPLOYEE_4_LAST_NAME,
        EMPLOYEE_4_USERNAME,
        EMPLOYEE_4_PASSWORD,
        EMPLOYEE_4_EMAIL,
        TEAM_2
    );
  }
}