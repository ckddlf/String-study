package com.example.domain.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails user
    ) {
        String email = user.getUsername();
        boolean result = likeService.toggleLike(postId, email);

        return ResponseEntity.ok(result ? "좋아요 추가됨" : "좋아요 취소됨");
    }

     @GetMapping("/{postId}/count")
    public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
        long count = likeService.countLikes(postId);
        return ResponseEntity.ok(count);
    }
}