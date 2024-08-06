package com.rebu.like.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.like.controller.dto.LikeCreateCommentRequest;
import com.rebu.like.controller.dto.LikeCreateFeedRequest;
import com.rebu.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/comment")
    public ResponseEntity<?> createCommentLike(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @RequestBody LikeCreateCommentRequest likeCreateCommentRequest) {
        // 토큰에서 닉네임 가져오기
        String requestUserNickname = "user123"; // 임시
        likeService.createCommentLike(likeCreateCommentRequest.toDto(requestUserNickname));
        return new ResponseEntity<>(new ApiResponse<>("1P00",null), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteCommentLike(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @PathVariable Long commentId) {
        // 토큰에서 닉네임 가져오기
        String requestUserNickname = "user123"; // 임시
        likeService.deleteCommentLike(requestUserNickname, commentId);
        return new ResponseEntity<>(new ApiResponse<>("1P01",null), HttpStatus.OK);
    }

    @PostMapping("/feed")
    public ResponseEntity<?> createFeedLike(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @RequestBody LikeCreateFeedRequest likeCreateFeedRequest) {
        // 토큰에서 닉네임 가져오기
        String requestUserNickname = "user123"; // 임시
        likeService.createFeedLike(likeCreateFeedRequest.toDto(requestUserNickname));
        return new ResponseEntity<>(new ApiResponse<>("1P02",null), HttpStatus.OK);
    }

    @DeleteMapping("/feed/{feedId}")
    public ResponseEntity<?> deleteFeedLike(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
    @PathVariable Long feedId) {
        // 토큰에서 닉네임 가져오기
        String requestUserNickname = "user123"; // 임시
        likeService.deleteFeedLike(requestUserNickname, feedId);
        return new ResponseEntity<>(new ApiResponse<>("1P03",null), HttpStatus.OK);
    }

}
