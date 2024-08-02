package com.rebu.profile.employee.entity;

import com.rebu.member.enums.Gender;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.shop.entity.ShopProfile;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class EmployeeProfile extends Profile {

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopProfile shop;

    @Column(length = 16)
    private String role;

    @Column(nullable = false, length = 16)
    private String workingName;

    @Column(length = 256)
    private String workingIntroduction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
}
