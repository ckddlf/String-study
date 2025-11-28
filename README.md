# Market API

JWT 기반 인증을 사용하는 중고나라 컨셉의 게시판 프로젝트입니다.  
회원가입, 로그인, 게시글 CRUD, 댓글 CRUD, 좋아요 기능을 제공합니다.
스프링 공부 목적으로 간단하게 제작함. 
---

## 🔑 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Spring Security (JWT)
- MySQL 8.x
- Maven
- Lombok

----

## 🔐 인증
JWT(Json Web Token)를 사용하여 로그인한 사용자만 일부 API를 사용할 수 있습니다.

- 회원가입: `/api/auth/signup` (POST)
- 로그인: `/api/auth/login` (POST)

로그인 시 JWT를 발급하며, Authorization 헤더에 `Bearer {token}` 형식으로 전달해야 합니다.


--

## 📌 API 목록 (표)

| 기능 | URL | Method | 인증 | 설명 |
|------|-----|--------|------|------|
| 회원가입 | /api/auth/signup | POST | ❌ | 신규 사용자 회원가입 |
| 로그인 | /api/auth/login | POST | ❌ | JWT 발급 로그인 |
| 게시글 작성 | /api/posts | POST | ✅ | 새 게시글 작성 |
| 게시글 수정 | /api/posts/{id} | PUT | ✅ | 게시글 수정 |
| 게시글 삭제 | /api/posts/{id} | DELETE | ✅ | 게시글 삭제 |
| 전체 게시글 조회 | /api/posts | GET | ✅ | 모든 게시글 조회 |
| 단일 게시글 조회 | /api/posts/{id} | GET | ✅ | 특정 게시글 조회 |
| 댓글 작성 | /api/posts/{postId}/comments | POST | ✅ | 특정 게시글에 댓글 작성 |
| 댓글 수정 | /api/comments/{id} | PUT | ✅ | 댓글 수정 |
| 댓글 삭제 | /api/comments/{id} | DELETE | ✅ | 댓글 삭제 |
| 댓글 조회 | /api/posts/{postId}/comments | GET | ✅ | 특정 게시글 댓글 조회 |
| 좋아요 | /api/posts/{postId}/likes | POST | ✅ | 게시글 좋아요 |
| 좋아요 취소 | /api/posts/{postId}/likes | DELETE | ✅ | 게시글 좋아요 취소 |
| 좋아요 수 조회 | /api/posts/{postId}/likes/count | GET | ✅ | 게시글 좋아요 수 조회 |

## 📌 API 예제

### 1️⃣ 회원가입

**Request**
POST /api/auth/signup
 ```json
  {
    "nickname": "user1",
    "email": "user1@example.com",
    "password": "password123"
  }
```
**Response**
```json
{
  "message": "회원가입 성공! 회원 ID 1"
}
```
### 2️⃣ 로그인

**Requst**
POST /api/auth/login
```json
{
  "email": "user1@example.com",
  "password": "password123"
}
```
**Response**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
}
```

### 3️⃣ 게시글 작성

**Request**
POST /api/posts
```json
{
  "title": "게시글 제목",
  "content": "게시글 내용",
  "price": 2500,
  "imageUrl": "https://example.com/image.png"
}
```
**Response**
```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "price": 2500,
  "imageUrl": "https://example.com/image.png",
  "nickname": "user1"
}
```

### 4️⃣ 게시글 수정

**Request**
PUT /api/posts/1
```json
{
  "title": "수정된 제목",
  "content": "수정된 내용",
  "price": 3000,
  "imageUrl": "https://example.com/updated-image.png"
}
```
**Response**
```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "수정된 내용",
  "price": 3000,
  "imageUrl": "https://example.com/updated-image.png",
  "nickname": "user1"
}
```

### 5️⃣ 게시글 삭제

**Request**
DELETE /api/posts/1

### 6️⃣ 전체 게시글 조회

**Request**
GET /api/posts

**Response**
```json
[
  {
    "id": 1,
    "title": "게시글 제목",
    "content": "게시글 내용",
    "price": 2500,
    "imageUrl": "https://example.com/image.png",
    "nickname": "user1"
  }
]
```

### 7️⃣ 단일 게시글 조회

**Request**
GET /api/posts/1
**Response**
```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "price": 2500,
  "imageUrl": "https://example.com/image.png",
  "nickname": "user1"
}
```

### 8️⃣ 댓글 작성

**Request**
POST /api/posts/1/comments
```json
{
  "content": "댓글 내용"
}
```
**Response**
```json
{
  "id": 1,
  "content": "댓글 내용",
  "authorEmail": "user1@example.com"
}
```

### 9️⃣ 댓글 수정

**Request**
PUT /api/comments/1
```json
{
  "content": "수정된 댓글 내용"
}
```

**Response**
```json
{
  "id": 1,
  "content": "수정된 댓글 내용",
  "authorEmail": "user1@example.com"
}
```

### 🔟 댓글 삭제

**Request**
DELETE /api/comments/1

### 1️⃣1️⃣ 댓글 조회

**Request**
GET /api/posts/1/comments

**Response**
```json
[
  {
    "id": 1,
    "content": "댓글 내용",
    "authorEmail": "user1@example.com"
  }
]
```

### 1️⃣2️⃣ 좋아요

**Request**
POST /api/posts/1/likes

**Response**
```json
{
  "message": "좋아요 추가됨"
}
```

### 1️⃣3️⃣ 좋아요 취소

**Request**
DELETE /api/posts/1/likes

**Response**
```json
{
  "message": "좋아요 취소됨"
}
```

### 1️⃣4️⃣ 좋아요 수 조회

**Request**
GET /api/posts/1/likes/count

**Response**
```json
{
  "count": 1
}
```
