package com.rebu.profile.controller.dto;

import com.rebu.profile.dto.ChangeNicknameDto;
import com.rebu.profile.validation.annotation.Nickname;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameRequest {

    @Nickname
    private String newNickname;

    public ChangeNicknameDto toDto(String oldNickname, HttpServletResponse response) {
        return ChangeNicknameDto.builder()
                .oldNickname(oldNickname)
                .newNickname(newNickname)
                .response(response)
                .build();
    }
}
