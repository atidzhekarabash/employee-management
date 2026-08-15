package com.employeemanagement.util;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Team;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TeamMocks {

  public static final UUID TEAM_1_ID = UUID.fromString("d3b07384-d9a3-4f11-9e5b-12de9b712b15");
  public static final String TEAM_1_NAME = "Development";

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
      null
  );

  public static final Team TEAM_1 = new Team(TEAM_1_NAME, EMPLOYEE_1);

  public static final UUID TEAM_2_ID = UUID.fromString("c3839bc7-c64e-44a5-9a85-64b81ffffda3");
  public static final String TEAM_2_NAME = "Product";

  public static final UUID EMPLOYEE_2_ID = UUID.fromString("3c7f2c4a-55d3-4f9b-9a2f-123456789abc");
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
      null
  );

  public static final Team TEAM_2 = new Team(TEAM_2_NAME, EMPLOYEE_2);

  public static final UUID TEAM_3_ID = UUID.fromString("b349a2c9-df9b-4a8e-a7a9-8d8b0e1e6e12");
  public static final String TEAM_3_NAME = "Marketing";
  public static final Team TEAM_3 = new Team(TEAM_3_NAME);

  public static final UUID TEAM_4_ID = UUID.fromString("9d8a9f48-8a4c-4f62-bf42-79bd1f0e5cd4");
  public static final String TEAM_4_NAME = "Sales";
  public static final Team TEAM_4 = new Team(TEAM_4_NAME);

  public static final List<Team> TEAMS = Arrays.asList(
      TEAM_1,
      TEAM_2,
      TEAM_3,
      TEAM_4
  );

  // Team Responses
  public static final TeamResponse TEAM_RESPONSE_1 = new TeamResponse(
      TEAM_1_ID,
      TEAM_1_NAME,
      EMPLOYEE_1_ID
  );

  public static final TeamResponse TEAM_RESPONSE_2 = new TeamResponse(
      TEAM_2_ID,
      TEAM_2_NAME,
      EMPLOYEE_2_ID
  );

  public static final TeamResponse TEAM_RESPONSE_3 = new TeamResponse(
      TEAM_3_ID,
      TEAM_3_NAME,
      null
  );

  public static final TeamResponse TEAM_RESPONSE_4 = new TeamResponse(
      TEAM_4_ID,
      TEAM_4_NAME,
      null
  );

  public static final List<TeamResponse> TEAM_RESPONSES = Arrays.asList(
      TEAM_RESPONSE_1,
      TEAM_RESPONSE_2,
      TEAM_RESPONSE_3,
      TEAM_RESPONSE_4
  );

  // Team Requests
  public static final TeamRequest TEAM_REQUEST_1 = new TeamRequest(
      TEAM_1_NAME,
      EMPLOYEE_1_ID
  );

  public static final TeamRequest TEAM_REQUEST_2 = new TeamRequest(
      TEAM_2_NAME,
      EMPLOYEE_2_ID
  );

  public static final TeamRequest TEAM_REQUEST_3 = new TeamRequest(
      TEAM_3_NAME,
      null
  );

  public static final TeamRequest TEAM_REQUEST_4 = new TeamRequest(
      TEAM_4_NAME,
      null
  );

  public static final List<TeamRequest> TEAM_REQUESTS = Arrays.asList(
      TEAM_REQUEST_1,
      TEAM_REQUEST_2,
      TEAM_REQUEST_3,
      TEAM_REQUEST_4
  );

  static {
    TEAM_1.setId(TEAM_1_ID);
    TEAM_2.setId(TEAM_2_ID);
    TEAM_3.setId(TEAM_3_ID);
    TEAM_4.setId(TEAM_4_ID);

    EMPLOYEE_1.setId(EMPLOYEE_1_ID);
    EMPLOYEE_1.setIsEnabled(true);

    EMPLOYEE_2.setId(EMPLOYEE_2_ID);
    EMPLOYEE_2.setIsEnabled(true);
  }
}