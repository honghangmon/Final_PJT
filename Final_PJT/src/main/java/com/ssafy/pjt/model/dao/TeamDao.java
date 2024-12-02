package com.ssafy.pjt.model.dao;

import java.util.List;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

public interface TeamDao {
    void insertTeam(Team team); // 팀 생성
    List<Team> findAllTeams(); // 전체 팀 리스트 조회
    Team findById(int teamId); // 팀 조회
    int updateTeam(Team team); // 팀 업데이트
    int deleteTeam(int teamId); // 팀 삭제

    int insertTeamMember(int teamId, int userId); // 팀 멤버 추가
    int deleteTeamMember(int teamId, int userId); // 팀 멤버 삭제
    List<User> findTeamMembers(int teamId); // 팀 멤버 조회
}
