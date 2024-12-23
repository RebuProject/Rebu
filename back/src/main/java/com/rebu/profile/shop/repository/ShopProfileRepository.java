package com.rebu.profile.shop.repository;

import com.rebu.profile.shop.dto.GetShopProfileResultDto;
import com.rebu.profile.shop.entity.ShopProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopProfileRepository extends JpaRepository<ShopProfile, Long> {
    Optional<ShopProfile> findByNickname(String nickname);

    @Query("""
        SELECT sp
        FROM ShopProfile sp
        LEFT JOIN FETCH sp.employeeProfiles
        WHERE sp.nickname = :nickname
    """)
    Optional<ShopProfile> findByNicknameFetch(String nickname);

    @Query("""
        SELECT new com.rebu.profile.shop.dto.GetShopProfileResultDto(
            sp.imageSrc,
            sp.nickname,
            sp.name,
            sp.introduction,
            sp.address,
            sp.phone,
            COUNT(DISTINCT fr.id),
            COUNT(DISTINCT fi.id),
            COUNT(DISTINCT fe.id),
            COUNT(DISTINCT re.id),
            COUNT(DISTINCT rs.id),
            sp.isPrivate
        )
        FROM ShopProfile sp
        LEFT JOIN Follow fr ON fr.follower.id = sp.id
        LEFT JOIN Follow fi ON fi.following.id = sp.id
        LEFT JOIN Feed fe ON fe.owner.id = sp.id
        LEFT JOIN Review re ON re.shopProfile.id = sp.id
        LEFT JOIN Reservation rs ON rs.shopProfile.id = sp.id
        WHERE sp.id = :profileId
        GROUP BY sp.id
    """)
    Optional<GetShopProfileResultDto> getShopProfileResponseByProfileId(Long profileId);

}
