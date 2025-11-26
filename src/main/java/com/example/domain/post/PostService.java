package com.example.domain.post;

import com.example.domain.member.Member;
import com.example.domain.member.MemberRepository;
import com.example.domain.post.dto.PostRequest;
import com.example.domain.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 작성
    @Transactional
    public PostResponse createPost(PostRequest request) {
        String email = getCurrentUserEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .nickname(member.getNickname()) // 작성자 닉네임
                .build();

        Post saved = postRepository.save(post);

        return mapToResponse(saved);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 게시글 단일 조회
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        return mapToResponse(post);
    }

    // 게시글 수정 (작성자만 가능)
    @Transactional
    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        String email = getCurrentUserEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        if (!post.getNickname().equals(member.getNickname())) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setPrice(request.getPrice());
        post.setImageUrl(request.getImageUrl());

        return mapToResponse(post);
    }

    // 게시글 삭제 (작성자만 가능)
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        String email = getCurrentUserEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        if (!post.getNickname().equals(member.getNickname())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    // 현재 로그인한 유저 email 가져오기
    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }
    }

    // Post -> PostResponse 매핑
    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .imageUrl(post.getImageUrl())
                .nickname(post.getNickname())
                .build();
    }
}