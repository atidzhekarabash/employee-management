package com.employeemanagement.util;

import com.employeemanagement.dto.EmployeeInsertRequest;
import com.employeemanagement.dto.EmployeeResponse;
import com.employeemanagement.dto.EmployeeUpdateRequest;
import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Team;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EmployeeMocks {

  // Team
  public static final UUID TEAM_1_ID = UUID.fromString("d3b07384-d9a3-4f11-9e5b-12de9b712b15");
  public static final String TEAM_1_NAME = "Development";
  public static final Team TEAM_1 = new Team(TEAM_1_NAME);

  public static final UUID TEAM_2_ID = UUID.fromString("c3839bc7-c64e-44a5-9a85-64b81ffffda3");
  public static final String TEAM_2_NAME = "Product";
  public static final Team TEAM_2 = new Team(TEAM_2_NAME);

  // Employee
  public static final UUID EMPLOYEE_1_ID = UUID.fromString("2cb43800-25e3-4134-bb39-b1a25d34390c");
  public static final String EMPLOYEE_1_FIRST_NAME = "John";
  public static final String EMPLOYEE_1_MIDDLE_NAME = "A.";
  public static final String EMPLOYEE_1_LAST_NAME = "Doe";
  public static final String EMPLOYEE_1_USERNAME = "john.doe";
  public static final String EMPLOYEE_1_PASSWORD = "password123";
  public static final String EMPLOYEE_1_EMAIL = "john.doe@example.com";
  public static final Employee EMPLOYEE_1 = new Employee(
      EMPLOYEE_1_FIRST_NAME,
      EMPLOYEE_1_MIDDLE_NAME,
      EMPLOYEE_1_LAST_NAME,
      EMPLOYEE_1_USERNAME,
      EMPLOYEE_1_PASSWORD,
      EMPLOYEE_1_EMAIL,
      TEAM_1
  );

  public static final UUID EMPLOYEE_2_ID = UUID.fromString("eabeb98f-f9da-4031-83d2-81c89f8ba029");
  public static final String EMPLOYEE_2_FIRST_NAME = "Jane";
  public static final String EMPLOYEE_2_MIDDLE_NAME = "B.";
  public static final String EMPLOYEE_2_LAST_NAME = "Smith";
  public static final String EMPLOYEE_2_USERNAME = "jane.smith";
  public static final String EMPLOYEE_2_PASSWORD = "password456";
  public static final String EMPLOYEE_2_EMAIL = "jane.smith@example.com";
  public static final Employee EMPLOYEE_2 = new Employee(
      EMPLOYEE_2_FIRST_NAME,
      EMPLOYEE_2_MIDDLE_NAME,
      EMPLOYEE_2_LAST_NAME,
      EMPLOYEE_2_USERNAME,
      EMPLOYEE_2_PASSWORD,
      EMPLOYEE_2_EMAIL,
      TEAM_1
  );

  public static final UUID EMPLOYEE_3_ID = UUID.fromString("a81e6fe8-b168-42cb-87ad-04bf65478904");
  public static final String EMPLOYEE_3_FIRST_NAME = "Mike";
  public static final String EMPLOYEE_3_MIDDLE_NAME = "C.";
  public static final String EMPLOYEE_3_LAST_NAME = "Johnson";
  public static final String EMPLOYEE_3_USERNAME = "mike.johnson";
  public static final String EMPLOYEE_3_PASSWORD = "password789";
  public static final String EMPLOYEE_3_EMAIL = "mike.johnson@example.com";
  public static final Employee EMPLOYEE_3 = new Employee(
      EMPLOYEE_3_FIRST_NAME,
      EMPLOYEE_3_MIDDLE_NAME,
      EMPLOYEE_3_LAST_NAME,
      EMPLOYEE_3_USERNAME,
      EMPLOYEE_3_PASSWORD,
      EMPLOYEE_3_EMAIL,
      TEAM_1
  );

  public static final UUID EMPLOYEE_4_ID = UUID.fromString("c7a636d1-a03c-4dd2-a94b-7a76e5a746b2");
  public static final String EMPLOYEE_4_FIRST_NAME = "Emily";
  public static final String EMPLOYEE_4_MIDDLE_NAME = "D.";
  public static final String EMPLOYEE_4_LAST_NAME = "Williams";
  public static final String EMPLOYEE_4_USERNAME = "emily.williams";
  public static final String EMPLOYEE_4_PASSWORD = "password000";
  public static final String EMPLOYEE_4_EMAIL = "emily.williams@example.com";
  public static final Employee EMPLOYEE_4 = new Employee(
      EMPLOYEE_4_FIRST_NAME,
      EMPLOYEE_4_MIDDLE_NAME,
      EMPLOYEE_4_LAST_NAME,
      EMPLOYEE_4_USERNAME,
      EMPLOYEE_4_PASSWORD,
      EMPLOYEE_4_EMAIL,
      TEAM_2
  );

  public static final UUID EMPLOYEE_5_ID = UUID.fromString("dee7aec3-0687-42e3-afbf-0084afd00440");
  public static final String EMPLOYEE_5_FIRST_NAME = "David";
  public static final String EMPLOYEE_5_MIDDLE_NAME = "E.";
  public static final String EMPLOYEE_5_LAST_NAME = "Brown";
  public static final String EMPLOYEE_5_USERNAME = "david.brown";
  public static final String EMPLOYEE_5_PASSWORD = "password999";
  public static final String EMPLOYEE_5_EMAIL = "david.brown@example.com";
  public static final Employee EMPLOYEE_5 = new Employee(
      EMPLOYEE_5_FIRST_NAME,
      EMPLOYEE_5_MIDDLE_NAME,
      EMPLOYEE_5_LAST_NAME,
      EMPLOYEE_5_USERNAME,
      EMPLOYEE_5_PASSWORD,
      EMPLOYEE_5_EMAIL,
      TEAM_2
  );

  public static final List<Employee> EMPLOYEES;

  static {
    TEAM_1.setId(TEAM_1_ID);
    TEAM_2.setId(TEAM_2_ID);

    TEAM_1.setLead(EMPLOYEE_1);

    EMPLOYEE_1.setId(EMPLOYEE_1_ID);
    EMPLOYEE_1.setIsEnabled(true);
    EMPLOYEE_2.setId(EMPLOYEE_2_ID);
    EMPLOYEE_2.setIsEnabled(true);
    EMPLOYEE_3.setId(EMPLOYEE_3_ID);
    EMPLOYEE_3.setIsEnabled(true);
    EMPLOYEE_4.setId(EMPLOYEE_4_ID);
    EMPLOYEE_4.setIsEnabled(true);
    EMPLOYEE_5.setId(EMPLOYEE_5_ID);
    EMPLOYEE_5.setIsEnabled(true);

    EMPLOYEES = Arrays.asList(EMPLOYEE_1, EMPLOYEE_2, EMPLOYEE_3, EMPLOYEE_4, EMPLOYEE_5);
  }

  // Team Response
  public static final TeamResponse TEAM_RESPONSE_1 = new TeamResponse(
      TEAM_1_ID,
      TEAM_1_NAME,
      EMPLOYEE_1_ID
  );

  public static final TeamResponse TEAM_RESPONSE_2 = new TeamResponse(
      TEAM_2_ID,
      TEAM_2_NAME,
      null
  );

  // Employee Response
  public static final EmployeeResponse EMPLOYEE_RESPONSE_1 = new EmployeeResponse(
      EMPLOYEE_1_ID,
      EMPLOYEE_1_FIRST_NAME,
      EMPLOYEE_1_MIDDLE_NAME,
      EMPLOYEE_1_LAST_NAME,
      EMPLOYEE_1_EMAIL,
      TEAM_RESPONSE_1
  );

  public static final EmployeeResponse EMPLOYEE_RESPONSE_2 = new EmployeeResponse(
      EMPLOYEE_2_ID,
      EMPLOYEE_2_FIRST_NAME,
      EMPLOYEE_2_MIDDLE_NAME,
      EMPLOYEE_2_LAST_NAME,
      EMPLOYEE_2_EMAIL,
      TEAM_RESPONSE_1
  );

  public static final EmployeeResponse EMPLOYEE_RESPONSE_3 = new EmployeeResponse(
      EMPLOYEE_3_ID,
      EMPLOYEE_3_FIRST_NAME,
      EMPLOYEE_3_MIDDLE_NAME,
      EMPLOYEE_3_LAST_NAME,
      EMPLOYEE_3_EMAIL,
      TEAM_RESPONSE_1
  );

  public static final EmployeeResponse EMPLOYEE_RESPONSE_4 = new EmployeeResponse(
      EMPLOYEE_4_ID,
      EMPLOYEE_4_FIRST_NAME,
      EMPLOYEE_4_MIDDLE_NAME,
      EMPLOYEE_4_LAST_NAME,
      EMPLOYEE_4_EMAIL,
      TEAM_RESPONSE_2
  );

  public static final EmployeeResponse EMPLOYEE_RESPONSE_5 = new EmployeeResponse(
      EMPLOYEE_5_ID,
      EMPLOYEE_5_FIRST_NAME,
      EMPLOYEE_5_MIDDLE_NAME,
      EMPLOYEE_5_LAST_NAME,
      EMPLOYEE_5_EMAIL,
      TEAM_RESPONSE_2
  );

  public static final List<EmployeeResponse> EMPLOYEE_RESPONSES = Arrays.asList(EMPLOYEE_RESPONSE_1,
      EMPLOYEE_RESPONSE_2, EMPLOYEE_RESPONSE_3, EMPLOYEE_RESPONSE_4, EMPLOYEE_RESPONSE_5);

  public static final String EMPLOYEE_1_UNHASHED_PASSWORD = "xHBactQ8Z7";
  public static final EmployeeInsertRequest EMPLOYEE_INSERT_REQUEST = new EmployeeInsertRequest(
      EMPLOYEE_1_FIRST_NAME,
      EMPLOYEE_1_MIDDLE_NAME,
      EMPLOYEE_1_LAST_NAME,
      EMPLOYEE_1_USERNAME,
      EMPLOYEE_1_UNHASHED_PASSWORD,
      EMPLOYEE_1_EMAIL,
      TEAM_1_ID
  );

  public static final EmployeeUpdateRequest EMPLOYEE_UPDATE_REQUEST = new EmployeeUpdateRequest(
      EMPLOYEE_1_FIRST_NAME,
      EMPLOYEE_1_MIDDLE_NAME,
      EMPLOYEE_1_LAST_NAME,
      EMPLOYEE_1_USERNAME,
      EMPLOYEE_1_EMAIL,
      TEAM_1_ID
  );
}
