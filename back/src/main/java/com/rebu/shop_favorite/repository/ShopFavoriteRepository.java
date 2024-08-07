package com.rebu.shop_favorite.repository;


import com.rebu.shop_favorite.entity.ShopFavorite;
import com.rebu.shop_favorite.entity.ShopFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopFavoriteRepository extends JpaRepository<ShopFavorite, ShopFavoriteId> {
}
