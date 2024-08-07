package com.rebu.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProfileResponse {
    private String imageSrc;
    private int followersCount;
    private int followingCount;
    private String nickname;
    private String introduction;
    private int reviewCnt;
    private int scrapCnt;
    private int favoritesCnt;
    private boolean isPrivate;
    private Relation relation;

    public enum Relation {
        OWN, FOLLOWING, NONE
    }
}
