package com.rebu.member.controller.dto;

import com.rebu.common.enums.Gender;
import com.rebu.member.dto.MemberJoinDto;
import com.rebu.member.validation.annotation.Email;
import com.rebu.member.validation.annotation.Password;
import com.rebu.profile.dto.ProfileGenerateDto;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberJoinRequest {
    @Email
    private String email;
    @Password
    private String password;
    private String name;
    private LocalDate birth;
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
