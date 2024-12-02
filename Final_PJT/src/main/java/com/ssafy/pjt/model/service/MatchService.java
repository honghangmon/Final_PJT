package com.ssafy.pjt.model.service;

import java.util.List;

import com.ssafy.pjt.model.dto.Match;

public interface MatchService {
    boolean createMatch(Match match); // 매치 생성
    Match getMatchById(int matchId); // matchId로 매치 조회
    boolean addTeamToMatch(int matchId, int team2Id); // 팀2 정보 추가 및 상태 업데이트
    boolean deleteMatch(int matchId); // matchId로 매치 삭제
    List<Match> getAllMatches(); // 전체 매치 조회
    List<Match> findMatchByCriteria(String sportsType, String location, String scheduledTime); // 같은 조건의 매치 조회
}
