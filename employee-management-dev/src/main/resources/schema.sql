DROP DATABASE IF EXISTS employee_management;
CREATE DATABASE employee_management;
USE employee_management;

CREATE TABLE employee
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    is_enabled  BOOLEAN      NOT NULL DEFAULT TRUE,
    team_id     BINARY(16)   NOT NULL
);

CREATE TABLE role
(
    id   BINARY(16)   NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE privilege
(
    id   BINARY(16)   NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE role_privilege
(
    role_id      BINARY(16) NOT NULL,
    privilege_id BINARY(16) NOT NULL,
    PRIMARY KEY (role_id, privilege_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (privilege_id) REFERENCES privilege (id)
);

CREATE TABLE employee_role
(
    employee_id BINARY(16) NOT NULL,
    role_id     BINARY(16) NOT NULL,
    PRIMARY KEY (employee_id, role_id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE team
(
    id      BINARY(16)   NOT NULL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL UNIQUE,
    lead_id BINARY(16) UNIQUE,
    FOREIGN KEY (lead_id) REFERENCES employee (id)
);

ALTER TABLE employee
    ADD CONSTRAINT fk_employee_team FOREIGN KEY (team_id) REFERENCES team (id);

CREATE TABLE employment_history
(
    id          BINARY(16)     NOT NULL PRIMARY KEY,
    title       VARCHAR(255)   NOT NULL,
    wage        DECIMAL(10, 2) NOT NULL,
    `date`      DATE           NOT NULL,
    employee_id BINARY(16)     NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE project
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE project_employee
(
    id          BINARY(16) NOT NULL PRIMARY KEY,
    is_manager  BOOLEAN    NOT NULL,
    project_id  BINARY(16) NOT NULL,
    employee_id BINARY(16) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES project (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE `leave`
(
    id           BINARY(16)                                        NOT NULL PRIMARY KEY,
    request_date DATE                                              NOT NULL,
    start_date   DATE                                              NOT NULL,
    end_date     DATE                                              NOT NULL,
    state        ENUM ('PENDING', 'APPROVED', 'DENIED')            NOT NULL DEFAULT 'PENDING',
    type         ENUM ('SICK_LEAVE', 'PAID_LEAVE', 'UNPAID_LEAVE') NOT NULL,
    employee_id  BINARY(16)                                        NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

CREATE TABLE leave_action
(
    id          BINARY(16)               NOT NULL PRIMARY KEY,
    type        ENUM ('APPROVE', 'DENY') NOT NULL,
    date        DATE                     NOT NULL,
    leave_id    BINARY(16)               NOT NULL,
    employee_id BINARY(16)               NOT NULL,
    FOREIGN KEY (leave_id) REFERENCES `leave` (id),
    FOREIGN KEY (employee_id) REFERENCES employee (id),
    UNIQUE (leave_id, employee_id)
);