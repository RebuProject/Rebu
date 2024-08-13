package com.rebu.follow.dto;

import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FollowerDto {
    private Profile profile;
    private Boolean isFollow;
}
