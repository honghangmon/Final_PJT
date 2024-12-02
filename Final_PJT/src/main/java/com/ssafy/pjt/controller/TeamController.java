package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.TeamService;

@RestController
@RequestMapping("/api/teams")
//@CrossOrigin("*")
public class TeamController {

	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	/**
	 * 팀 생성
	 */
	@PostMapping
	public ResponseEntity<?> createTeam(@RequestBody Team team) { // 팀을 생성하며 leaderId로 등록하는 사람은 자동으로 member에 추가해야함 -> 수정 필요 ->INSERT 쿼리에 useGeneratedKeys와 keyProperty를 설정한 후 add하는 메서드를 넣어 해결!
	    // 팀 생성
	    teamService.createTeam(team);
//	    System.out.println(team);

	    // 팀 생성 후 leaderId를 팀 멤버로 자동 추가
	    boolean leaderAdded = teamService.addMemberToTeam(team.getTeamId(), team.getLeaderId());
	    if (leaderAdded) {
	        return new ResponseEntity<>(Map.of("message", "Team created successfully, leader added as member"), HttpStatus.CREATED);
	    } else {
	        return new ResponseEntity<>(Map.of("message", "Failed to add leader as member after team creation"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	/**
	 * 전체 팀 리스트 조회
	 */
	@GetMapping
	public ResponseEntity<?> getAllTeams() {
		List<Team> teams = teamService.getAllTeams();
		return new ResponseEntity<>(teams, HttpStatus.OK);
	}

	/**
	 * 팀 조회
	 */
	@GetMapping("/{teamId}")
	public ResponseEntity<?> getTeamById(@PathVariable("teamId") int teamId) {
		Team team = teamService.getTeamById(teamId);
		if (team != null) {
			return new ResponseEntity<>(team, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Team not found"), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 팀 업데이트
	 */
	@PutMapping("/{teamId}")
	public ResponseEntity<?> updateTeam(@PathVariable("teamId") int teamId, @RequestBody Team team) {
		team.setTeamId(teamId);
		List<User> members = teamService.getTeamMembers(teamId);
//		System.out.println(team.getLeaderId());
		boolean isTeamMember = false;
		for (int i = 0; i < members.size(); i++) {	// 새롭게 입력된 leaderId가 team에 속해있는지 파악하기 위한 for문. json으로 입력된 leaderId를 새로운 Team 객체 team의 leaderId로 설정하고 members의 userId와 비교하여 같은 게 있으면 true, 없으면 false
			if (members.get(i).getUserId() == team.getLeaderId()) {
				isTeamMember = true;
				break;
			}
		}
		if (!isTeamMember) {
			return new ResponseEntity<>(Map.of("message", "Leader must be a team member"), HttpStatus.BAD_REQUEST);
		}
		boolean updated = teamService.updateTeam(team);
		if (updated) {
			return new ResponseEntity<>(Map.of("message", "Team updated successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to update team"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 팀 삭제
	 */
	@DeleteMapping("/{teamId}")
	public ResponseEntity<?> deleteTeam(@PathVariable("teamId") int teamId) {
		boolean deleted = teamService.deleteTeam(teamId);
		if (deleted) {
			return new ResponseEntity<>(Map.of("message", "Team deleted successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to delete team"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 팀 멤버 추가
	 */
	@PostMapping("/{teamId}/members")
	public ResponseEntity<?> addMemberToTeam(@PathVariable("teamId") int teamId, @RequestBody User user) {
		int userId = user.getUserId(); // User 객체에서 userId 추출
//		System.out.println(user);
		boolean added = teamService.addMemberToTeam(teamId, userId);
		if (added) {
			return new ResponseEntity<>(Map.of("message", "User added to team successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to add user to team"), HttpStatus.BAD_REQUEST);
		}
	}
	// 멤버 추가 후 팀 멤버 조회할 때 userId가 0이 됨 (그냥 userId와 team에 속해있는 userId가 다름..!)

	/**
	 * 팀 멤버 삭제
	 */
	@DeleteMapping("/{teamId}/members/{userId}")
	public ResponseEntity<?> removeMemberFromTeam(@PathVariable("teamId") int teamId, @PathVariable("userId") int userId) {
		boolean removed = teamService.removeMemberFromTeam(teamId, userId);
		if (removed) {
			return new ResponseEntity<>(Map.of("message", "User removed from team successfully"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Failed to remove user from team"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 팀 멤버 조회
	 */
	@GetMapping("/{teamId}/members")
	public ResponseEntity<?> getTeamMembers(@PathVariable("teamId") int teamId) {
		List<User> members = teamService.getTeamMembers(teamId);
		return new ResponseEntity<>(members, HttpStatus.OK);
	}
}
