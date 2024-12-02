# API 문서

## 사용자 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /users/signup          | 회원가입(새 사용자를 생성)         | `{ "username": "testuser", "email": "test@example.com", "password": "password123" }` | `{ "message": "User registered successfully" }`         |
| POST        | /users/login           | 로그인 처리                        | `{ "username": "testuser", "password": "password123" }`      | `{ "message": "Login successful", "user": { "id": 1, "username": "testuser", "email": "test@example.com" } }` |
| POST        | /users/logout          | 로그아웃 처리                      | N/A                                                        | `{ "message": "Logout successful" }`                     |
| GET         | /users/{id}            | ID로 사용자 세부정보 조회          | N/A                                                        | `{ "id": 1, "username": "testuser", "email": "test@example.com" }` |
| PUT         | /users/{id}            | 사용자 정보 업데이트              | `{ "username": "updateduser", "email": "new@example.com" }` | `{ "id": 1, "username": "updateduser", "email": "new@example.com" }` |
| DELETE      | /users/{id}            | 사용자 삭제                       | N/A                                                        | `{ "message": "사용자가 삭제되었습니다." }`                |

---

## 팀 관리

| HTTP 메서드 | API path                     | 설명                                    | Request Body                        | Response Body                   |
|-------------|-------------------------------|-----------------------------------------|-------------------------------------|---------------------------------|
| POST        | /teams                        | 새로운 팀을 생성합니다                  | `{ "teamName": "Team A", "leaderId": 1 }` | `{ "teamId": 1, "teamName": "Team A", "leaderId": 1 }` |
| GET         | /teams                        | 전체 팀 리스트 조회                     | N/A                                 | `[ { "teamId": 1, "teamName": "Team A", "leaderId": 1 }, { "teamId": 2, "teamName": "Team B", "leaderId": 2 } ]` |
| GET         | /teams/{id}                   | ID로 팀 세부정보 조회                   | N/A                                 | `{ "teamId": 1, "teamName": "Team A", "leaderId": 1 }` |
| PUT         | /teams/{id}                   | 팀 정보 업데이트                        | `{ "teamName": "Updated Team Name" }` | `{ "teamId": 1, "teamName": "Updated Team Name", "leaderId": 1 }` |
| DELETE      | /teams/{id}                   | 팀 삭제                                 | N/A                                 | `{ "message": "팀이 삭제되었습니다." }`                 |
| POST        | /teams/{teamId}/members       | 팀에 사용자를 추가합니다                | `{ "userId": 123 }`                 | `{ "message": "User added to team successfully." }` |
| DELETE      | /teams/{teamId}/members/{userId} | 팀에서 사용자를 제거합니다         | N/A                                 | `{ "message": "User removed from team successfully." }` |
| GET         | /teams/{teamId}/members       | 팀의 멤버 목록 조회                     | N/A                                 | `[ { "id": 1, "username": "user1" }, { "id": 2, "username": "user2" } ]` |

---

## 경기 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /matches               | 새로운 매칭 요청을 생성합니다      | `{ "team1Id": 1, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer" }` | `{ "matchId": 1, "team1Id": 1, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "pending" }` |
| GET         | /matches/{id}          | ID로 경기 세부정보 조회           | N/A                                                        | `{ "matchId": 1, "team1Id": 1, "team2Id": 2, "scheduledTime": "2024-12-01T15:00:00Z", "location": "Seoul Stadium", "sportsType": "soccer", "status": "confirmed" }` |
| PUT         | /matches/{id}/team2    | 매칭 시도: 팀2 정보를 추가         | `{ "team2Id": 2 }`                                          | `{ "message": "Match updated successfully", "matchId": 1, "team1Id": 1, "team2Id": 2 }` |
| DELETE      | /matches/{id}          | 경기 삭제                         | N/A                                                        | `{ "message": "매치가 삭제되었습니다." }`                |
