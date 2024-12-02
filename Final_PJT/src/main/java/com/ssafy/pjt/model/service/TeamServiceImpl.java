package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.TeamDao;
import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamDao teamDao;

    public TeamServiceImpl(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @Override
    public void createTeam(Team team) {
        teamDao.insertTeam(team);
    }

    @Override
    public List<Team> getAllTeams() {
    	return teamDao.findAllTeams();
    }
    
    @Override
    public Team getTeamById(int teamId) {
        return teamDao.findById(teamId);
    }

    @Override
    public boolean updateTeam(Team team) {
        return teamDao.updateTeam(team) > 0;
    }

    @Override
    public boolean deleteTeam(int teamId) {
        return teamDao.deleteTeam(teamId) > 0;
    }

    @Override
    public boolean addMemberToTeam(int teamId, int userId) {
        return teamDao.insertTeamMember(teamId, userId) > 0;
    }

    @Override
    public boolean removeMemberFromTeam(int teamId, int userId) {
        return teamDao.deleteTeamMember(teamId, userId) > 0;
    }

    @Override
    public List<User> getTeamMembers(int teamId) {
        return teamDao.findTeamMembers(teamId);
    }

}
