package com.ssafy.pjt.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dao.UserDao;
import com.ssafy.pjt.model.dto.Team;
import com.ssafy.pjt.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void signup(User user) {
		// 비밀번호 해시 처리 (추후 구현 가능)
		userDao.insertUser(user);
	}

	@Override
	public User login(String userName, String password) {
		User user = userDao.findByUserName(userName);
		if (user != null && user.getPassword().equals(password)) {
			return user; // 로그인 성공
		}
		return null; // 로그인 실패
	}

	@Override
	public User getUserById(int userId) {
		return userDao.findById(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.findAllUsers();
	}
	
	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user) > 0;
	}

	@Override
	public boolean deleteUser(int userId) {
		return userDao.deleteUser(userId) > 0;
	}

	@Override
	public List<Team> getTeamsForUser(int userId) {
		return userDao.findTeamsByUserId(userId);
	}
}
