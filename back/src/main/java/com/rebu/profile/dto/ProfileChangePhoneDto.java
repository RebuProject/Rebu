package com.rebu.profile.dto;

import com.rebu.profile.validation.annotation.Phone;
import lombok.Getter;

@Getter
public class ProfileChangePhoneDto {
    @Phone
    private String phone;
}
