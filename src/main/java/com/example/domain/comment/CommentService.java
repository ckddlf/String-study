package com.example.domain.comment;

import com.example.domain.member.MemberRepository;

import com.example.domain.comment.dto.CommentRequest;
import com.example.domain.comment.dto.CommentResponse;
import com.example.domain.member.Member;
import com.example.domain.post.Post;
import com.example.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 현재 로그인한 유저 가져오기
     // 현재 로그인한 유저 가져오기
    private Member getCurrentUser() {
        // principal에서 username(email) 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else if (principal instanceof String) { // 일부 상황에서 principal이 String(email)일 수 있음
            email = (String) principal;
        } else {
            throw new RuntimeException("인증 정보 없음");
        }

        // DB에서 Member 조회
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보 없음"));
    }

    // 댓글 작성
    @Transactional
    public CommentResponse create(Long postId, CommentRequest request) {
        Member member = getCurrentUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .author(member)
                .build();

        Comment saved = commentRepository.save(comment);

        return CommentResponse.builder()
                .id(saved.getId())
                .content(saved.getContent())
                .authorEmail(saved.getAuthor().getEmail())
                .build();
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .content(c.getContent())
                        .authorEmail(c.getAuthor().getEmail())
                        .build())
                .toList();
    }

    // 댓글 수정
    @Transactional
    public void update(Long commentId, CommentRequest request) {
        Member member = getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!comment.getAuthor().getId().equals(member.getId())) {
            throw new RuntimeException("작성자만 수정 가능");
        }

        comment.update(request.getContent());
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId) {
        Member member = getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!comment.getAuthor().getId().equals(member.getId())) {
            throw new RuntimeException("작성자만 삭제 가능");
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteLikesByPost(Post post) {
        commentRepository.deleteAllByPostId(post.getId());
    }
}