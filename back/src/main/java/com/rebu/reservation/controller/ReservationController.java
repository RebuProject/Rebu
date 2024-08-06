package com.rebu.reservation.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.reservation.controller.dto.ReservationCreateRequest;
import com.rebu.reservation.service.ReservationService;
import com.rebu.security.dto.AuthProfileInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                    @Valid @RequestBody ReservationCreateRequest request){
        reservationService.create(request.toDto(authProfileInfo.getNickname()));
        return ResponseEntity.ok(new ApiResponse<>("1R00", null));
    }
}
