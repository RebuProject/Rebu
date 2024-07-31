package com.rebu.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthProfileInfo {
    private String nickname;
    private String password;
    private String email;
    private String type;
}
