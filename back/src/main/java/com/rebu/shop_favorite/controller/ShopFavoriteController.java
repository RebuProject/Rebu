package com.rebu.shop_favorite.controller;

import com.rebu.common.controller.dto.ApiResponse;
import com.rebu.security.dto.AuthProfileInfo;
import com.rebu.shop_favorite.controller.dto.AddFavoriteRequest;
import com.rebu.shop_favorite.service.ShopFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop-favorites")
public class ShopFavoriteController {

    private final ShopFavoriteService shopFavoriteService;

    @PostMapping
    public ResponseEntity<?> addFavorite(@AuthenticationPrincipal AuthProfileInfo authProfileInfo,
                                         @RequestBody AddFavoriteRequest addFavoriteRequest) {
        shopFavoriteService.addFavorite(addFavoriteRequest.toDto(authProfileInfo.getNickname()));
        return ResponseEntity.ok(new ApiResponse<>("매장 즐겨찾기 등록", null));
    }

}
