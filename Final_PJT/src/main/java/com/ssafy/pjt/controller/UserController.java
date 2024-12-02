package com.ssafy.pjt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        userService.signup(user);
        return new ResponseEntity<>(Map.of("message", "User registered successfully"), HttpStatus.CREATED);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
        User loggedInUser = userService.login(user.getUserName(), user.getPassword());
        if (loggedInUser != null) {
            session.setAttribute("user", loggedInUser);
            return new ResponseEntity<>(Map.of("message", "Login successful", "user", loggedInUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")	// session의 상태를 바꾸는 요청이므로 get보단 post로 하는 것이 RestAPI에 적합? 하다고 하네요
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(Map.of("message", "Logout successful"), HttpStatus.OK);
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "User not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
	 * 전체 유저 리스트 조회
	 */
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
    	List<User> users = userService.getAllUsers();
//    	System.out.println(users);
    	return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * 사용자 정보 업데이트
     */
    @PutMapping("/{userId}")	// 업데이트하면서 session에 있는 user의 정보도 수정해야하므로 session에 updatedUser를 저장함
    public ResponseEntity<?> updateUser(@PathVariable("userId") int userId, @RequestBody User user, HttpSession session) {
        user.setUserId(userId); // URL의 id를 객체에 설정
        boolean updated = userService.updateUser(user);
        if (updated) {
        	User updatedUser = userService.getUserById(userId);
        	session.setAttribute("user", updatedUser);
            return new ResponseEntity<>(Map.of("message", "User updated successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Failed to update user"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 사용자 삭제
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId, HttpSession session) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
        	session.invalidate();
            return new ResponseEntity<>(Map.of("message", "User deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "Failed to delete user"), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 특정 유저가 속한 팀 리스트 조회
     */
    @GetMapping("/{userId}/teams")
    public ResponseEntity<?> getTeamsForUser(@PathVariable("userId") int userId) {
        List<Team> teams = userService.getTeamsForUser(userId);
        if (teams != null && !teams.isEmpty()) {
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("message", "User is not part of any team"), HttpStatus.NOT_FOUND);
        }
    }
}
