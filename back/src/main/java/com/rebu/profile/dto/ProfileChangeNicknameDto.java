package com.rebu.profile.dto;

import com.rebu.profile.validation.annotation.Nickname;
import lombok.Getter;

@Getter
public class ProfileChangeNicknameDto {
    @Nickname
    private String nickname;
}
