
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

## 매치 요청

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/match-requests    | 매치 요청 생성                   | `{ "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z", "userId": 1, "gender": "any" }` | `{ "matchRequestId": 1, "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z", "userId": 1 }` |
| GET         | /api/match-requests/{userId} | 사용자의 매치 요청 조회       | N/A                                                        | `{ "matchRequests": [{ "matchRequestId": 1, "sport": "soccer", "location": "Gangnam", "time": "2024-11-09T14:00:00Z", "matched": true, "opponentTeam": { "teamId": 2, "teamName": "Opponent Team" } }] }` |

## 구장 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| GET         | /api/stadiums/availability | 구장 예약 가능 여부 확인         | N/A                                                        | `{ "stadiums": [{ "stadiumId": 1, "name": "강남 축구장", "available": true }] }` |

## 알림 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/notifications      | 새 알림 생성                      | `{ "userId": 1, "message": "당신의 매치 요청이 수락되었습니다." }` | `{ "notificationId": 1, "userId": 1, "message": "당신의 매치 요청이 수락되었습니다." }` |
| GET         | /api/notifications/{userId} | 사용자의 알림 조회              | N/A                                                        | `{ "notifications": [{ "notificationId": 1, "userId": 1, "message": "당신의 매치 요청이 수락되었습니다." }] }` |

## 예약 관리

| HTTP 메서드 | API path              | 설명                               | Request Body                                                   | Response Body                                                |
|-------------|------------------------|------------------------------------|------------------------------------------------------------|---------------------------------------------------------|
| POST        | /api/reservations      | 새 예약 생성                      | `{ "stadiumId": 1, "userId": 1, "time": "2024-11-09T14:00:00Z" }` | `{ "reservationId": 1, "stadiumId": 1, "userId": 1, "time": "2024-11-09T14:00:00Z" }` |
| GET         | /api/reservations/{userId}| 사용자의 예약 조회               | N/A                                                        | `{ "reservations": [{ "reservationId": 1, "stadiumId": 1, "userId": 1, "time": "2024-11-09T14:00:00Z" }] }` |
