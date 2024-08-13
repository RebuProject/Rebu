package com.rebu.follow.controller.dto;

import com.rebu.follow.dto.FollowerDto;
import com.rebu.profile.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFollowerResponse {
    private String nickname;
    private String imgSrc;
    private String introduction;
    private Type type;
    private Boolean isFollow;
    private Long followId;

    public static GetFollowerResponse from(FollowerDto followerDto) {
        return GetFollowerResponse.builder()
                .nickname(followerDto.getProfile().getNickname())
                .imgSrc(followerDto.getProfile().getImageSrc())
                .introduction(followerDto.getProfile().getIntroduction())
                .type(followerDto.getProfile().getType())
                .isFollow(followerDto.getIsFollow())
                .followId(followerDto.getFollowId())
                .build();
    }
}
