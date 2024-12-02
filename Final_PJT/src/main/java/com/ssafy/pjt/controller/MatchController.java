package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.pjt.model.dto.Match;
import com.ssafy.pjt.model.service.MatchService;

@RestController
@RequestMapping("/api/matches")
//@CrossOrigin("*")
public class MatchController {

	private final MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * 매칭 생성
	 */
	@PostMapping
	public ResponseEntity<?> createMatch(@RequestBody Match match) {
		boolean created = matchService.createMatch(match);
		if (created) {
			return new ResponseEntity<>(match, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to create match"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * matchId로 매치 조회
	 */
	@GetMapping("/{matchId}")
	public ResponseEntity<?> getMatchById(@PathVariable int matchId) {
		Match match = matchService.getMatchById(matchId);
		if (match != null) {
			return new ResponseEntity<>(match, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Match not found"), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 팀2 정보 추가 및 매칭 상태 업데이트
	 */
	@PutMapping("/{matchId}")
	public ResponseEntity<?> addTeamToMatch(@PathVariable int matchId, @RequestBody Map<String, Integer> body) {
		int team2Id = body.get("team2Id");
		boolean updated = matchService.addTeamToMatch(matchId, team2Id);
		if (updated) {
			return new ResponseEntity<>(Map.of("message", "Match updated successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to update match"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * matchId로 매치 삭제
	 */
	@DeleteMapping("/{matchId}")
	public ResponseEntity<?> deleteMatch(@PathVariable int matchId) {
		boolean deleted = matchService.deleteMatch(matchId);
		if (deleted) {
			return new ResponseEntity<>(Map.of("message", "Match deleted successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to delete match"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 전체 매치 조회
	 */
	@GetMapping
	public ResponseEntity<?> getAllMatches() {
		List<Match> matches = matchService.getAllMatches();
		return new ResponseEntity<>(matches, HttpStatus.OK);
	}
	
	/**
	 * 같은 조건의 매치 조회
	 */
	@GetMapping("/check")
	public ResponseEntity<?> checkMatch(@RequestParam String sportsType, @RequestParam String location,
			@RequestParam String scheduledTime) {
		// Criteria를 기반으로 매칭 조회
		List<Match> matches = matchService.findMatchByCriteria(sportsType, location, scheduledTime);
		if (!matches.isEmpty()) {
			return new ResponseEntity<>(matches, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

}
