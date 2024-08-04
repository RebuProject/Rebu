package com.rebu.profile.employee.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.profile.employee.controller.dto.GenerateEmployeeProfileRequest;
import com.rebu.profile.employee.service.EmployeeProfileService;
import com.rebu.profile.exception.NicknameDuplicateException;
import com.rebu.security.dto.AuthProfileInfo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @PostMapping
    public ResponseEntity<?> generateEmployeeProfile(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                                     @Valid @ModelAttribute GenerateEmployeeProfileRequest generateEmployeeProfileRequest,
                                                     HttpServletResponse response,
                                                     @SessionAttribute(name = "CheckNickname:generateProfile", required = false) String nickname) {
        if (nickname == null || !nickname.equals(generateEmployeeProfileRequest.getNickname())) {
            throw new NicknameDuplicateException();
        }
        employeeProfileService.generateEmployeeProfile(generateEmployeeProfileRequest.toDto(authProfileInfo.getEmail(), authProfileInfo.getNickname()), response);
        return ResponseEntity.ok(new ApiResponse<>("직원 프로필 생성 완료 코드", null));
    }
}
