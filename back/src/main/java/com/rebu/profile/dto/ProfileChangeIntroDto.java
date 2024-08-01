package com.rebu.profile.dto;

import com.rebu.profile.validation.annotation.Introduction;
import lombok.Getter;

@Getter
public class ProfileChangeIntroDto {
    @Introduction
    private String introduction;
}
