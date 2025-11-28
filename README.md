# Market API

JWT 기반 인증을 사용하는 게시판 프로젝트입니다.  
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

---

## 🔐 인증
JWT(Json Web Token)를 사용하여 로그인한 사용자만 일부 API를 사용할 수 있습니다.

- 회원가입: `/api/auth/signup` (POST)
- 로그인: `/api/auth/login` (POST)

로그인 시 JWT를 발급하며, Authorization 헤더에 `Bearer {token}` 형식으로 전달해야 합니다.


---

## 📌 API 목록

## 📌 API 목록 (표)

| 기능 | URL | Method | 인증 | 설명 |
|------|-----|--------|------|------|
| 회원가입 | /api/auth/signup | POST | ❌ | 신규 사용자 회원가입 |
| 로그인 | /api/auth/login | POST | ❌ | JWT 발급 로그인 |
| 게시글 작성 | /api/posts | POST | ✅ | 새 게시글 작성 |
| 게시글 수정 | /api/posts/{id} | PUT | ✅ | 게시글 수정 |
| 게시글 삭제 | /api/posts/{id} | DELETE | ✅ | 게시글 삭제 |
| 전체 게시글 조회 | /api/posts | GET | ❌ | 모든 게시글 조회 |
| 단일 게시글 조회 | /api/posts/{id} | GET | ❌ | 특정 게시글 조회 |
| 댓글 작성 | /api/posts/{postId}/comments | POST | ✅ | 특정 게시글에 댓글 작성 |
| 댓글 수정 | /api/comments/{id} | PUT | ✅ | 댓글 수정 |
| 댓글 삭제 | /api/comments/{id} | DELETE | ✅ | 댓글 삭제 |
| 댓글 조회 | /api/posts/{postId}/comments | GET | ❌ | 특정 게시글 댓글 조회 |
| 좋아요 | /api/posts/{postId}/likes | POST | ✅ | 게시글 좋아요 |
| 좋아요 취소 | /api/posts/{postId}/likes | DELETE | ✅ | 게시글 좋아요 취소 |
| 좋아요 수 조회 | /api/posts/{postId}/likes/count | GET | ❌ | 게시글 좋아요 수 조회 |

