package com.rebu.workingInfo.controller;

import com.rebu.common.aop.annotation.Authorized;
import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.profile.enums.Type;
import com.rebu.workingInfo.controller.dto.WorkingInfoUpdqteRequest;
import com.rebu.workingInfo.service.WorkingInfoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/working-info")
@AllArgsConstructor
public class WorkingInfoController {

    private final WorkingInfoService workingInfoService;

    @PostMapping
    @Authorized(allowed = {Type.SHOP})
    public ResponseEntity<?> create() {/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
//        String requestUserNickname =  authProfileInfo.getNickname();
        String requestUserNickname = "user123s"; // 임시
        workingInfoService.create(requestUserNickname);
        return new ResponseEntity<>(new ApiResponse<>("1K00",null), HttpStatus.OK);
    }

    @PutMapping
    @Authorized(allowed = {Type.SHOP})
    public ResponseEntity<?> update(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @Valid @RequestBody WorkingInfoUpdqteRequest workingInfoUpdqteRequest) {
//        String requestUserNickname =  authProfileInfo.getNickname();
        String requestUserNickname = "user123"; // 임시
        workingInfoService.update(workingInfoUpdqteRequest.toDto(requestUserNickname));
        return new ResponseEntity<>(new ApiResponse<>("1K01",null), HttpStatus.OK);
    }
}
