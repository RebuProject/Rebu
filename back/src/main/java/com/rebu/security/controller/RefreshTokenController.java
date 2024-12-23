package com.rebu.security.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.security.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtTokenService jwtTokenService;

    @PostMapping("/auths/refresh")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        jwtTokenService.reissue(request, response);
        return ResponseEntity.ok(new ApiResponse<>("1A06", null));
    }
}
