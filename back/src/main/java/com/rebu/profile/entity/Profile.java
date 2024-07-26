package com.rebu.profile.entity;

import com.rebu.common.enums.Status;
import com.rebu.member.entity.Member;
import com.rebu.profile.enums.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Builder
@DynamicInsert
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
    @ColumnDefault("'COMMON'")
    private Type type;

    @Column(length = 512)
    private String imageSrc;

    @Column(length = 256)
    private String introduction;

    //@ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime recentTime;

    @Column(length = 16, nullable = false)
    private String phone;

    @ColumnDefault("false")
    private Boolean isPrivate;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    private Status status;
}
