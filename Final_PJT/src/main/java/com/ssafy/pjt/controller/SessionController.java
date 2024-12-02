package com.ssafy.pjt.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pjt.model.dto.User;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/session")
//@CrossOrigin("*") 이거 있으면 인증상태 관리하기 위한 .allowedOrigins("http://localhost:5173") 이걸 못 씀
public class SessionController {
	
	@GetMapping
	public ResponseEntity<?> checkSession(HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	        return new ResponseEntity<>(Map.of("authenticated", true, "user", user), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(Map.of("authenticated", false), HttpStatus.UNAUTHORIZED);
	    }
	}

}
