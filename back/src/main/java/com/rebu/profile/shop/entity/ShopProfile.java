package com.rebu.profile.shop.entity;

import com.rebu.profile.entity.Profile;
import com.rebu.profile.shop.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class ShopProfile extends Profile {

    @Column(unique=true, nullable = false)
    private String LicenseNum;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String address;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private int reservationInterval;

    protected void onCreate() {
        if (reservationInterval == 0) {
            reservationInterval = 10;
        }
    }

}
