package com.rebu.feed.review.controller.dto;

import com.rebu.common.validation.annotation.NotNull;
import com.rebu.feed.review.dto.ReviewCreateDto;
import com.rebu.feed.review.validation.annotation.ReviewKeywordIds;
import com.rebu.feed.review.validation.annotation.ReviewRating;
import com.rebu.feed.validation.annotation.FeedContent;
import com.rebu.feed.validation.annotation.FeedImages;
import com.rebu.feed.validation.annotation.Hashtags;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {
    @FeedImages
    private List<MultipartFile> images;
    @NotNull
    private Long reservationId;
    @ReviewKeywordIds
    private List<Long> reviewKeywordIds;
    @ReviewRating
    private Integer rating;
    @FeedContent
    private String content;
    @Hashtags
    private List<String> hashtags;

    public ReviewCreateDto toDto(String nickname){
        return ReviewCreateDto.builder()
                .images(this.images)
                .reservationId(this.reservationId)
                .reviewKeywordIds(this.reviewKeywordIds)
                .rating(this.rating)
                .content(this.content)
                .hashtags(this.hashtags)
                .nickname(nickname).build();
    }

}
