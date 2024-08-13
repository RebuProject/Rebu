package com.rebu.follow.dto;

import com.rebu.follow.entity.Follow;
import com.rebu.profile.enums.Type;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetFollowingResponse {
    private Long followId;
    private String nickname;
    private String imgSrc;
    private String introduction;
    private Type type;

    public static GetFollowingResponse from(Follow follow) {
        return GetFollowingResponse.builder()
                .followId(follow.getId())
                .nickname(follow.getFollowing().getNickname())
                .imgSrc(follow.getFollowing().getImageSrc())
                .introduction(follow.getFollowing().getIntroduction())
                .type(follow.getFollowing().getType())
                .build();
    }
}
