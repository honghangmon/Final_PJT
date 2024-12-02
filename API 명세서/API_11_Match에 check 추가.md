# API 문서

## 사용자 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /users/signup          | 회원가입(새 사용자를 생성)         | `{ "username": "testuser", "email": "test@example.com", "password": "password123" }` | `{ "message": "User registered successfully" }`         |
| POST        | /users/login           | 로그인 처리                        | `{ "username": "testuser", "password": "password123" }`      | `{ "message": "Login successful", "user": { "userId": 1, "username": "testuser", "email": "test@example.com" } }` |
| POST        | /users/logout          | 로그아웃 처리                      | N/A                                                        | `{ "message": "Logout successful" }`                     |
| GET         | /users                 | 전체 유저 리스트 조회              | N/A                                                        | `[ { "userId": 1, "username": "testuser", "email": "test@example.com" }, { "userId": 2, "username": "testuser2", "email": "test2@example.com" } ... ]` |
| GET         | /users/{userId}        | userId로 사용자 세부정보 조회      | N/A                                                        | `{ "userId": 1, "username": "testuser", "email": "test@example.com" }` |
| PUT         | /users/{userId}        | 사용자 정보 업데이트              | `{ "username": "updateduser", "email": "new@example.com" }` | `{ "userId": 1, "username": "updateduser", "email": "new@example.com" }` |
| DELETE      | /users/{userId}        | 사용자 삭제                       | N/A                                                        | `{ "message": "User deleted successfully" }`                |
| GET         | /users/{userId}/teams  | userId로 사용자가 속한 팀 리스트 조회 | N/A                                                        | `[ { "teamId": 1, "teamName": "Team A", "leaderId": 1 }, { "teamId": 2, "teamName": "Team B", "leaderId": 2 } ]` |
| GET         | /session               | 현재 세션의 유저 정보 확인         | N/A                                                        | `{ "authenticated": true, "user": { "userId": 1, "username": "testuser", "email": "test@example.com" } }` 또는 `{ "authenticated": false }` |

---

## 팀 관리

| HTTP 메서드 | API path                     | 설명                                    | Request Body                        | Response Body                   |
|-------------|-------------------------------|-----------------------------------------|-------------------------------------|---------------------------------|
| POST        | /teams                        | 새로운 팀을 생성합니다                  | `{ "teamName": "Team A", "leaderId": 1 }` | `{ "teamId": 1, "teamName": "Team A", "leaderId": 1 }` |
| GET         | /teams                        | 전체 팀 리스트 조회                     | N/A                                 | `[ { "teamId": 1, "teamName": "Team A", "leaderId": 1 }, { "teamId": 2, "teamName": "Team B", "leaderId": 2 } ]` |
| GET         | /teams/{teamId}               | teamId로 팀 세부정보 조회               | N/A                                 | `{ "teamId": 1, "teamName": "Team A", "leaderId": 1 }` |
| PUT         | /teams/{teamId}               | 팀 정보 업데이트                        | `{ "teamName": "Updated Team Name", "leaderId": 1 }` | `{ "teamId": 1, "teamName": "Updated Team Name", "leaderId": 1 }` |
| DELETE      | /teams/{teamId}               | 팀 삭제                                 | N/A                                 | `{ "message": "Team deleted successfully" }`                 |
| POST        | /teams/{teamId}/members       | 팀에 사용자를 추가합니다                | `{ "userId": 123 }`                 | `{ "message": "User added to team successfully" }` |
| DELETE      | /teams/{teamId}/members/{userId} | 팀에서 사용자를 제거합니다             | N/A                                 | `{ "message": "User removed from team successfully" }` |
| GET         | /teams/{teamId}/members       | 팀의 멤버 목록 조회                     | N/A                                 | `[ { "userId": 1, "username": "user1" }, { "userId": 2, "username": "user2" } ]` |

---

## 경기 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /matches               | 새로운 매칭 요청을 생성합니다      | `{ "team1Id": 1, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer" }` | `{ "matchId": 1, "team1Id": 1, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "pending" }` |
| GET         | /matches               | 전체 매치 리스트 조회              | N/A          | `[ { "matchId": 1, "team1Id": 1, "team2Id": 2, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "confirmed" }, ... ]` |
| GET         | /matches/{matchId}     | matchId로 경기 세부정보 조회       | N/A                                                        | `{ "matchId": 1, "team1Id": 1, "team2Id": 2, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "confirmed" }` |
| GET         | /matches/check         | 입력된 조건으로 매칭 체크          | N/A                                                        | `{ "matchId": 1, "team1Id": 1, "team2Id": null, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "pending" }` |
| PUT         | /matches/{matchId}     | 매칭 시도: 팀2 정보를 추가         | `{ "team2Id": 2 }`                                          | `{ "message": "Match updated successfully", "matchId": 1, "team1Id": 1, "team2Id": 2 }` |
| DELETE      | /matches/{matchId}     | 경기 삭제                         | N/A                                                        | `{ "message": "Match deleted successfully" }`                 |
