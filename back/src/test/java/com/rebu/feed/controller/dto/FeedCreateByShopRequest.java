package com.rebu.feed.controller.dto;

import com.rebu.feed.dto.FeedCreateByShopDto;
import com.rebu.feed.validation.annotation.FeedContent;
import com.rebu.feed.validation.annotation.FeedImages;
import com.rebu.feed.validation.annotation.FeedRegSelects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class FeedCreateByShopRequest {
    @FeedImages
    private List<MultipartFile> images;
    @FeedContent
    private String content;

    public FeedCreateByShopDto toDto(String nickname) {
        return FeedCreateByShopDto.builder()
                .images(this.images)
                .nickname(nickname)
                .content(this.content)
                .build();
    }
}