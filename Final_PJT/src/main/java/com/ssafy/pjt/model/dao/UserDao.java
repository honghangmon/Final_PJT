package com.ssafy.pjt.model.dao;

import java.util.List;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

public interface UserDao {
    void insertUser(User user); // 사용자 추가
    User findByUserName(String userName); // 사용자 조회 (사용자 이름으로)
    User findById(int userId); // 사용자 조회 (ID로)
    List<User> findAllUsers(); // 전체 유저 리스트 조회
    int updateUser(User user); // 사용자 정보 업데이트
    int deleteUser(int userId); // 사용자 삭제
    List<Team> findTeamsByUserId(int userId); // 특정 유저가 속한 팀 리스트 조회
}
