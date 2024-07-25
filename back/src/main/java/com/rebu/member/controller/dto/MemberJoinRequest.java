package com.rebu.member.controller.dto;

import com.rebu.common.enums.Gender;
import com.rebu.member.dto.MemberJoinDto;
import com.rebu.profile.dto.ProfileGenerateDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private LocalDateTime birth;
    private Gender gender;
    private String nickname;
    private String phone;

    public MemberJoinDto toMemberJoinDto() {
        return MemberJoinDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .birth(birth)
                .gender(gender)
                .build();
    }

    public ProfileGenerateDto toProfileGenerateDto() {
        return ProfileGenerateDto.builder()
                .nickname(nickname)
                .phone(phone)
                .build();
    }
}
