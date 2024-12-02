package com.ssafy.pjt.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.pjt.model.dto.Match;

public interface MatchDao {
	int insertMatch(Match match); // 매치 생성
	Match findMatchById(int matchId); // matchId로 매치 조회
	int updateMatchWithTeam2(Match match); // 팀2 정보 추가 및 상태 업데이트
	int deleteMatchById(int matchId); // matchId로 매치 삭제
	List<Match> findAllMatches(); // 전체 매치 조회
	List<Match> findMatchByCriteria(@Param("sportsType") String sportsType, @Param("location") String location, @Param("scheduledTime") String scheduledTime); // 같은 조건의 매치 조회
}
