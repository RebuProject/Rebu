package com.rebu.profile.entity;

import com.rebu.common.enums.Status;
import com.rebu.member.entity.Member;
import com.rebu.profile.enums.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type = Type.COMMON;

    @Column(length = 512)
    private String imageSrc;

    @Column(length = 256)
    private String introduction;

    @Column(nullable = false)
    private LocalDateTime recentTime = LocalDateTime.now();

    @Column(length = 12, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Boolean isPrivate = false;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NORMAL;
}
