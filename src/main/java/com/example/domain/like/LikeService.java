package com.example.domain.like;

import com.example.domain.member.Member;
import com.example.domain.member.MemberRepository;
import com.example.domain.post.Post;
import com.example.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean toggleLike(Long postId,String memberEmail) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없음"));

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));

        // 이미 좋아요를 눌렀는지 확인
        Like like = likeRepository.findByPostAndMember(post, member);

        if (like == null) {
            // 없으면 좋아요 추가
            like = Like.builder()
                    .post(post)
                    .member(member)
                    .build();

            likeRepository.save(like);

            return true; // true = 좋아요 추가됨
        } else {
            // 있으면 좋아요 취소
            likeRepository.delete(like);
            return false; // false = 좋아요 취소됨
        }
    }

     // 게시글 좋아요 개수 조회
    @Transactional(readOnly = true)
    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    // 게시글 삭제 시 좋아요 삭제
    @Transactional
    public void deleteLikesByPost(Post post) {
        likeRepository.deleteAllByPostId(post.getId());
    }
}