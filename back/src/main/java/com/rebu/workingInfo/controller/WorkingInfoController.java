package com.rebu.workingInfo.controller;

import com.rebu.common.aop.annotation.Authorized;
import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.profile.enums.Type;
import com.rebu.security.dto.AuthProfileInfo;
import com.rebu.workingInfo.controller.dto.WorkingInfoUpdateRequest;
import com.rebu.workingInfo.service.WorkingInfoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/working-info")
@AllArgsConstructor
public class WorkingInfoController {

    private final WorkingInfoService workingInfoService;

    @PutMapping
    @Authorized(allowed = {Type.SHOP})
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
        @Valid @RequestBody WorkingInfoUpdateRequest workingInfoUpdqteRequest) {
        String requestUserNickname =  authProfileInfo.getNickname();
        workingInfoService.update(workingInfoUpdqteRequest.toDto(requestUserNickname));
        return new ResponseEntity<>(new ApiResponse<>("1K01",null), HttpStatus.OK);
    }
}
