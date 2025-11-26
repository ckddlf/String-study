package com.example.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 나중에 검색, 필터 기능도 여기서 구현 가능
}