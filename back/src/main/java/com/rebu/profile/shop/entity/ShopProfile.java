package com.rebu.profile.shop.entity;

import com.rebu.profile.entity.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
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

    @ColumnDefault("10")
    private int reservationInterval;
}
