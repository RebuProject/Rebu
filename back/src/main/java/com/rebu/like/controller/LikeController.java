package com.rebu.like.controller;

import com.rebu.like.controller.dto.LikeCreateRequest;
import com.rebu.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<?> create(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @RequestParam(value = "commentId", required = false) Long commentId) {
        // 토큰에서 닉네임 가져오기
        String requestUserNickname = "user123";
        likeService.createCommentLike(requestUserNickname, commentId);

    }


}
