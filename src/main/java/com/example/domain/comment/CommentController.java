package com.example.domain.comment;

import com.example.domain.comment.dto.CommentRequest;
import com.example.domain.comment.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.create(postId, request));
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponse>> read(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request
    ) {
        commentService.update(commentId, request);
        return ResponseEntity.ok().build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}