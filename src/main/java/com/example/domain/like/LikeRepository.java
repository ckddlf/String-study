package com.example.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.example.domain.post.Post;
import com.example.domain.member.Member;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 유저가 특정 게시글 좋아요 했는지 확인
    Optional<Like> findByMemberIdAndPostId(Long memberId, Long postId);

    // 게시글 좋아요 개수
    Long countByPostId(Long postId);

    // 게시글의 좋아요 모두 삭제 (게시글 삭제 시 필요)
    void deleteAllByPostId(Long postId);

    Like findByPostAndMember(Post post, Member member);
}