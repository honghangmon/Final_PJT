package com.ssafy.pjt.model.service;

import java.util.List;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

public interface UserService {
    void signup(User user); // 회원가입
    User login(String userName, String password); // 로그인
    User getUserById(int userId); // 사용자 정보 조회
    List<User> getAllUsers();
    boolean updateUser(User user); // 사용자 정보 업데이트
    boolean deleteUser(int userId); // 사용자 삭제
    List<Team> getTeamsForUser(int userId); // 특정 유저가 속한 팀 리스트 조회
}
