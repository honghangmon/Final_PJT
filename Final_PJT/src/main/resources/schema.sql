-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS brbrdb;

-- 데이터베이스 사용
USE brbrdb;

-- 기존 테이블 삭제
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS team_members;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;

-- Users 테이블 생성
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    age INT NOT NULL,
    gender ENUM('male', 'female') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Teams 테이블 생성
CREATE TABLE teams (
    team_id INT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(100) NOT NULL,
    leader_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (leader_id) REFERENCES users(user_id) ON DELETE CASCADE -- 이렇게 되면 ON DELETE CASCADE로 인해 팀 리더가 삭제될 때 팀도 삭제되는 위험이 생김 -> 팀 리더가 삭제될 때 leader_id를 update 하는 방법으로 유지? or leader_id null로 남기기? or 팀 리더를 넘겨야 삭제 가능하게 하기
);

-- Team_members 테이블 생성
CREATE TABLE team_members (
    team_id INT NOT NULL,
    user_id INT NOT NULL,
    joined_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
  
-- Matches 테이블 생성
CREATE TABLE matches (
    match_id INT AUTO_INCREMENT PRIMARY KEY,
    team1_id INT NOT NULL,
    team2_id INT DEFAULT NULL,          -- 매칭 확정 전까지 NULL
    scheduled_time DATETIME NOT NULL,
    location VARCHAR(255) NOT NULL,
    sports_type VARCHAR(50) NOT NULL,
    status ENUM('pending', 'confirmed') NOT NULL DEFAULT 'pending',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (team1_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (team2_id) REFERENCES teams(team_id) ON DELETE CASCADE
);

SELECT * FROM users;
SELECT * FROM teams;
SELECT * FROM team_members;
SELECT * FROM matches;
