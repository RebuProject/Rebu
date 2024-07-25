package com.rebu.member.entity;

import com.rebu.common.enums.Gender;
import com.rebu.common.enums.Status;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false, unique = true)
    private String email;

    @Column(length = 256)
    private String password;

    private LocalDateTime birth;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime regDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 32, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.NORMAL;
}
