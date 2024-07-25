package com.rebu.member.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.member.controller.dto.MemberJoinRequest;
import com.rebu.member.entity.Member;
import com.rebu.member.service.MemberService;
import com.rebu.profile.service.ProfileService;
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
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<?> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        Member member = memberService.join(memberJoinRequest.toMemberJoinDto());
        profileService.generateProfile(memberJoinRequest.toProfileGenerateDto(), member);
        return ResponseEntity.ok(new ApiResponse<>("1A00", null));
    }
}
