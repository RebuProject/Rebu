package com.rebu.member.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.member.controller.dto.MemberJoinRequest;
import com.rebu.member.dto.MemberChangePasswordDto;
import com.rebu.member.service.MemberService;
import com.rebu.member.validation.annotation.Email;
import com.rebu.member.validation.annotation.EmailCheckPurpose;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest.toMemberJoinDto(), memberJoinRequest.toProfileGenerateDto());
        return ResponseEntity.ok(new ApiResponse<>("회원가입 성공 코드", null));
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@Email @RequestParam String email, @EmailCheckPurpose @RequestParam String purpose) {
        Boolean isExist = memberService.checkEmailDuplicated(email, purpose);
        return ResponseEntity.ok(new ApiResponse<>("이메일 중복 검사 성공", isExist));
    }

    @PatchMapping("/{email}/password")
    public ResponseEntity<?> changePassword(@PathVariable String email, @Valid @RequestBody MemberChangePasswordDto memberChangePasswordDto) {
        memberService.changePassword(email, memberChangePasswordDto);
        return ResponseEntity.ok(new ApiResponse<>("비밀번호 변경 성공 코드", null));
    }
}
