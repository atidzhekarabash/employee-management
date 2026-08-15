package com.employeemanagement.service.impl;

import com.employeemanagement.dto.TeamRequest;
import com.employeemanagement.dto.TeamResponse;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.mapper.TeamMapper;
import com.employeemanagement.model.Team;
import com.employeemanagement.repository.TeamRepository;
import com.employeemanagement.service.TeamService;
import com.employeemanagement.util.MessageUtil;
import com.employeemanagement.validator.service.TeamValidator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

  private final TeamRepository teamRepository;
  private final TeamMapper teamMapper;
  private final TeamValidator teamValidator;
  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

  public TeamServiceImpl(TeamRepository teamRepository,
      TeamMapper teamMapper, TeamValidator teamValidator, MessageUtil messageUtil) {
    this.teamRepository = teamRepository;
    this.teamMapper = teamMapper;
    this.teamValidator = teamValidator;
    this.messageUtil = messageUtil;
  }

  @Override
  public TeamResponse getTeamById(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Fetching team with id: {}", id);

    Team team = retrieveTeamById(id);

    return teamMapper.toTeamResponse(team);
  }

  @Override
  public List<TeamResponse> getAllTeams() {
    logger.debug("Fetching all teams");

    return teamRepository.findAll().stream().map(teamMapper::toTeamResponse).toList();
  }

  @Override
  public UUID createTeam(TeamRequest teamRequest) {
    Objects.requireNonNull(teamRequest);
    logger.debug("Creating a new team");

    teamValidator.validateCreation(teamRequest);

    Team team = teamMapper.toTeam(teamRequest);
    return teamRepository.save(team).getId();
  }

  @Override
  public void updateTeam(UUID id, TeamRequest teamRequest) {
    Objects.requireNonNull(id);
    Objects.requireNonNull(teamRequest);
    logger.debug("Updating team with id: {}", id);

    Team team = retrieveTeamById(id);
    teamValidator.validateUpdate(teamRequest, team);

    teamRepository.save(teamMapper.toTeam(teamRequest, team));
  }

  @Override
  public void deleteTeam(UUID id) {
    Objects.requireNonNull(id);
    logger.debug("Deleting team with id: {}", id);

    teamValidator.validateDeletion(id);
    teamRepository.deleteById(id);
  }

  private Team retrieveTeamById(UUID id) {
    return teamRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(
            messageUtil.getMessage("team.not_found", id)
        ));
  }
}
