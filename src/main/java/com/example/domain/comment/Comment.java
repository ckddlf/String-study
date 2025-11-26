package com.example.domain.comment;

import com.example.domain.member.Member;
import com.example.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    // 어떤 게시글에 달린 댓글인지
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    // 수정
    public void update(String content) {
        this.content = content;
    }
}