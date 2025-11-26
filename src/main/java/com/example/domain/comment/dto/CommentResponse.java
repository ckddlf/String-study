package com.example.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String authorEmail;
}