# Employee Management System

## Overview

The Employee Management System is a backend application designed to support common HR and administrative processes within an organization.

The system provides functionality for managing employees, teams, roles, privileges, and employee leave requests. Access to different operations is controlled through role-based permissions, allowing users to perform actions according to their responsibilities.

### Current supported roles

- Admin
- Team Lead
- Employee

Additional functionality and roles are planned for future releases.

## Features

### Use Cases

![Use Case Diagram](images/use_case.png)

- **Manage Leaves** – Admins can manage and monitor employee leave requests and their approval status.
- **Manage Employee Profiles** – Admins can create, update, and delete employee profiles.
- **Approve Leaves** – Team Leads can approve or reject leave requests submitted by employees.
- **Request Leave** – Employees can submit leave requests for a specific period.
- **Read Colleague Leaves** – Employees can view the leave status of their colleagues.
- **Login** – Users must authenticate before accessing the system.

### Roles

- **Admin** – Has the highest level of access and can manage employees, leave requests, roles, and privileges.
- **Team Lead** – Can review and approve or reject leave requests submitted by team members.
- **Employee** – Can request leave and view the leave status of colleagues.

## Entity-Relationship Diagram

![Entity-Relationship Diagram](images/db.png)

## Entity Description

- **Employee** – Stores employee information and user data required for authentication.
- **EmployeeRole** – Associates employees with their assigned roles.
- **Role** – Defines the roles available within the system.
- **RolePrivilege** – Associates roles with their permitted privileges.
- **Privilege** – Defines individual permissions available within the system.
- **Team** – Represents organizational teams and their team leads.
- **Leave** – Stores employee leave requests and related information.
- **LeaveAction** – Tracks actions performed on leave requests.

## Planned Entities

The following entities are planned for future releases:

- **EmploymentHistory** – Stores an employee's employment history, including job title and salary information.
- **Project** – Represents projects managed within the organization.
- **ProjectEmployee** – Associates employees with projects and identifies project managers.

## Project Structure

The application follows a layered architecture with separate components for configuration, controllers, DTOs, exception handling, mapping, models, repositories, services, utilities, and validation.

