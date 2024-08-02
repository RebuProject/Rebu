package com.rebu.profile.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ProfileChangeIsPrivateDto {
    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
