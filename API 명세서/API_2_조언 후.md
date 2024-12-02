# API 문서

## 사용자 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/users             | 새 사용자를 생성                  | `{ "username": "testuser", "email": "test@example.com", "password": "password123" }` | `{ "id": 1, "username": "testuser", "email": "test@example.com" }` |
| GET         | /api/users/{id}        | ID로 사용자 세부정보 조회         | N/A                                                        | `{ "id": 1, "username": "testuser", "email": "test@example.com" }` |
| PUT         | /api/users/{id}        | 사용자 정보 업데이트              | `{ "username": "updateduser", "email": "new@example.com" }` | `{ "id": 1, "username": "updateduser", "email": "new@example.com" }` |
| DELETE      | /api/users/{id}        | 사용자 삭제                       | N/A                                                        | `{ "message": "사용자가 삭제되었습니다." }`                |

## 팀 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/teams             | 새 팀 생성                        | `{ "teamName": "My Soccer Team", "sport": "soccer", "userId": 1 }` | `{ "teamId": 1, "teamName": "My Soccer Team", "sport": "soccer", "members": [1] }` |
| GET         | /api/teams/{id}        | ID로 팀 세부정보 조회             | N/A                                                        | `{ "teamId": 1, "teamName": "My Soccer Team", "sport": "soccer", "members": [1] }` |
| PUT         | /api/teams/{id}        | 팀 정보 업데이트                  | `{ "teamName": "Updated Team Name", "sport": "volleyball" }` | `{ "teamId": 1, "teamName": "Updated Team Name", "sport": "volleyball" }` |
| DELETE      | /api/teams/{id}        | 팀 삭제                           | N/A                                                        | `{ "message": "팀이 삭제되었습니다." }`                 |

## 매치 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/matches           | 매치 자동 생성                    | `{ "teamAId": 1, "teamBId": 2, "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z" }` | `{ "matchId": 1, "teamAId": 1, "teamBId": 2, "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z" }` |
| GET         | /api/matches/{id}      | 매치 세부 정보 조회               | N/A                                                        | `{ "matchId": 1, "teamAId": 1, "teamBId": 2, "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z" }` |
| DELETE      | /api/matches/{id}      | 매치 삭제                         | N/A                                                        | `{ "message": "매치가 삭제되었습니다." }`                |
