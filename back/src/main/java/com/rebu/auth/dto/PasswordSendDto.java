package com.rebu.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordSendDto {
    private String password;
    private String purpose;
}
