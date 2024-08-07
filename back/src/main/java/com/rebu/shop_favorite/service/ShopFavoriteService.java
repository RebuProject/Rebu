package com.rebu.shop_favorite.service;

import com.rebu.profile.entity.Profile;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.profile.shop.entity.ShopProfile;
import com.rebu.profile.shop.repository.ShopProfileRepository;
import com.rebu.shop_favorite.dto.AddFavoriteDto;
import com.rebu.shop_favorite.dto.DeleteFavoriteDto;
import com.rebu.shop_favorite.entity.ShopFavoriteId;
import com.rebu.shop_favorite.repository.ShopFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopFavoriteService {

    private final ShopFavoriteRepository shopFavoriteRepository;
    private final ProfileRepository profileRepository;
    private final ShopProfileRepository shopProfileRepository;

    @Transactional
    public void addFavorite(AddFavoriteDto addFavoriteDto) {
        Profile profile = profileRepository.findByNickname(addFavoriteDto.getNickname())
                .orElseThrow(ProfileNotFoundException::new);

        ShopProfile shopProfile = shopProfileRepository.findByNickname(addFavoriteDto.getShopNickname())
                .orElseThrow(ProfileNotFoundException::new);

        shopFavoriteRepository.save(addFavoriteDto.toEntity(profile, shopProfile));
    }

    @Transactional
    public void removeFavorite(DeleteFavoriteDto deleteFavoriteDto) {
        Profile profile = profileRepository.findByNickname(deleteFavoriteDto.getNickname())
                .orElseThrow(ProfileNotFoundException::new);

        ShopProfile shopProfile = shopProfileRepository.findByNickname(deleteFavoriteDto.getShopNickname())
                .orElseThrow(ProfileNotFoundException::new);

        ShopFavoriteId shopFavoriteId = ShopFavoriteId.builder()
                .shopProfile(shopProfile)
                .profile(profile)
                .build();

        shopFavoriteRepository.deleteById(shopFavoriteId);
    }
}
