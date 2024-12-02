package com.ssafy.pjt.model.service;

import java.util.List;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

public interface TeamService {
    void createTeam(Team team); // 팀 생성
    List<Team> getAllTeams(); // 전체 팀 리스트 조회
    Team getTeamById(int teamId); // 팀 조회
    boolean updateTeam(Team team); // 팀 업데이트
    boolean deleteTeam(int teamId); // 팀 삭제

    boolean addMemberToTeam(int teamId, int userId); // 팀 멤버 추가
    boolean removeMemberFromTeam(int teamId, int userId); // 팀 멤버 삭제
    List<User> getTeamMembers(int teamId); // 팀 멤버 조회
}
