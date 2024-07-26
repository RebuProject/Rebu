package com.rebu.member.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.member.controller.dto.MemberJoinRequest;
import com.rebu.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest.toMemberJoinDto(), memberJoinRequest.toProfileGenerateDto());
        return ResponseEntity.ok(new ApiResponse<>("1A00", null));
    }
}
