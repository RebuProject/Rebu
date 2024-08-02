package com.rebu.profile.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.profile.dto.*;
import com.rebu.profile.service.ProfileService;
import com.rebu.profile.validation.annotation.Nickname;
import com.rebu.profile.validation.annotation.NicknameCheckPurpose;
import com.rebu.profile.validation.annotation.Phone;
import com.rebu.profile.validation.annotation.PhoneCheckPurpose;
import com.rebu.security.dto.AuthProfileInfo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@Nickname @RequestParam String nickname,
                                           @NicknameCheckPurpose @RequestParam String purpose) {
        Boolean isExist = profileService.checkNicknameDuplicated(nickname, purpose);
        return ResponseEntity.ok(new ApiResponse<>("닉네임 중복 검사 성공 코드", isExist));
    }

    @GetMapping("/check-phone")
    public ResponseEntity<?> checkPhone(@Phone @RequestParam String phone,
                                        @PhoneCheckPurpose @RequestParam String purpose) {
        Boolean isExist = profileService.checkPhoneDuplicated(phone, purpose);
        return ResponseEntity.ok(new ApiResponse<>("전화번호 중복 검사 성공 코드", isExist));
    }

    @PatchMapping("/{nickname}/nickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                            @Valid @RequestBody ProfileChangeNicknameDto profileChangeNicknameDto,
                                            HttpServletResponse response) {
        profileService.changeNickname(authProfileInfo.getNickname(), profileChangeNicknameDto,response);
        return ResponseEntity.ok(new ApiResponse<>("닉네임 변경 완료 코드", null));
    }

    @PatchMapping("/{nickname}/introduction")
    public ResponseEntity<?> updateIntro(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                         @Valid @RequestBody ProfileChangeIntroDto profileChangeIntroDto) {
        profileService.changeIntro(authProfileInfo.getNickname(), profileChangeIntroDto);
        return ResponseEntity.ok(new ApiResponse<>("프로필 소개 변경 완료 코드", null));
    }

    @PatchMapping("/{nickname}/is-private")
    public ResponseEntity<?> updateisPrivate(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                             @RequestBody ProfileChangeIsPrivateDto profileChangeIsPrivateDto) {
        profileService.changeIsPrivate(authProfileInfo.getNickname(), profileChangeIsPrivateDto);
        return ResponseEntity.ok(new ApiResponse<>("프로필 공개 여부 변경 완료 코드", null));
    }

    @PatchMapping("/{nickname}/image")
    public ResponseEntity<?> updateProfileImg(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                              @Valid @RequestBody ProfileChangeImgDto profileChangeImgDto) {
        profileService.changePhoto(authProfileInfo.getNickname(), profileChangeImgDto);
        return ResponseEntity.ok(new ApiResponse<>("프로필 사진 변경 성공", null));
    }

    @PatchMapping("/{nickname}/phone")
    public ResponseEntity<?> updatePhone(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                         @Valid @RequestBody ProfileChangePhoneDto profileChangePhoneDto) {
        profileService.changePhone(authProfileInfo.getNickname(), profileChangePhoneDto);
        return ResponseEntity.ok(new ApiResponse<>("전화번호 변경 완료 코드", null));
    }
}
