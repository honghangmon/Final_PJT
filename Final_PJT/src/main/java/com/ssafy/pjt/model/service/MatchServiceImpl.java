package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.MatchDao;
import com.ssafy.pjt.model.dto.Match;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchDao matchDao;

    public MatchServiceImpl(MatchDao matchDao) {
        this.matchDao = matchDao;
    }

    @Override
    public boolean createMatch(Match match) {
        int rowsInserted = matchDao.insertMatch(match);
        return rowsInserted > 0;
    }

    @Override
    public Match getMatchById(int matchId) {
        return matchDao.findMatchById(matchId);
    }

    @Override
    public boolean addTeamToMatch(int matchId, int team2Id) {
        Match match = new Match();
        match.setMatchId(matchId);
        match.setTeam2Id(team2Id);
        int rowsUpdated = matchDao.updateMatchWithTeam2(match);
        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteMatch(int matchId) {
        int rowsDeleted = matchDao.deleteMatchById(matchId);
        return rowsDeleted > 0;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchDao.findAllMatches();
    }
    
    public List<Match> findMatchByCriteria(String sportsType, String location, String scheduledTime) {
        return matchDao.findMatchByCriteria(sportsType, location, scheduledTime);
    }
}
