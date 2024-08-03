package com.rebu.reviewkeyword.dto;

import com.rebu.reviewkeyword.entity.ReviewKeyword;
import lombok.Builder;

@Builder
public class ReviewKeywordDto {
    private Long id;
    private String keyword;

    public static ReviewKeywordDto from(ReviewKeyword reviewKeyword) {
        return ReviewKeywordDto.builder().keyword(reviewKeyword.getKeyword()).id(reviewKeyword.getId()).build();
    }
}
