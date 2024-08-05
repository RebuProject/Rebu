package com.rebu.scrap.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.scrap.controller.dto.ScrapCreateRequest;
import com.rebu.scrap.service.ScrapService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scraps")
@AllArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<?> create(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @RequestBody ScrapCreateRequest scrapCreateRequest) {
//        String requestUserNickname =  authProfileInfo.getNickname();
        String requestUserNickname = "user123"; // 임시
        scrapService.create(scrapCreateRequest.toDto(requestUserNickname));
        return new ResponseEntity<>(new ApiResponse<>("1N00",null), HttpStatus.OK);
    }

    @DeleteMapping("{scrapId}")
    public ResponseEntity<?> delete(/*@AuthenticationPrincipal AuthProfileInfo authProfileInfo,*/
        @PathVariable Long scrapId) {
        //        String requestUserNickname =  authProfileInfo.getNickname();
        String requestUserNickname = "user123"; // 임시
        scrapService.delete(scrapId, requestUserNickname);
        return new ResponseEntity<>(new ApiResponse<>("1N01",null), HttpStatus.OK);
    }

}
