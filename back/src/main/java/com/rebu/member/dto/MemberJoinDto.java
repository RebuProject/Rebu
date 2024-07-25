package com.rebu.member.dto;

import com.rebu.common.enums.Gender;
import com.rebu.member.entity.Member;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class MemberJoinDto {
    private String email;
    private String password;
    private String name;
    private LocalDateTime birth;
    private Gender gender;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .birth(birth)
                .gender(gender)
                .build();
    }
}
