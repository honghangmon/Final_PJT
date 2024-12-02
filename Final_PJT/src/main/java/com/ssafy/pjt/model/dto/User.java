package com.ssafy.pjt.model.dto;

public class User {

	private int userId;
	private String userName;
	private String password;
	private String nickname;
	private String email;
	private String phoneNumber;
	private int age;
	private String gender;
	private String createdAt;
	private String updatedAt;

	public User() {

	}

	public User(int userId, String userName, String password, String nickname, String email, String phoneNumber,
			int age, String gender, String createdAt, String updatedAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.gender = gender;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", nickname=" + nickname
				+ ", email=" + email + ", phoneNumber=" + phoneNumber + ", age=" + age + ", gender=" + gender
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
