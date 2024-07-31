package com.rebu.feed.dto;

import com.rebu.common.validation.annotation.NotNull;
import com.rebu.feed.entity.Feed;
import com.rebu.feed.validation.annotation.FeedContent;
import com.rebu.feed.validation.annotation.FeedImages;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.validation.annotation.Nickname;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
public class FeedCreateByShopDto {
    @NotNull
    private Long feedId;
    @FeedImages
    private List<MultipartFile> images;
    @Nickname
    private String nickname;
    @FeedContent
    private String content;

    public Feed toEntity(Profile writer) {
        return Feed.builder()
                .writer(writer)
                .owner(writer)
                .content(content)
                .type(Feed.Type.NONE)
                .build();
    }
}
