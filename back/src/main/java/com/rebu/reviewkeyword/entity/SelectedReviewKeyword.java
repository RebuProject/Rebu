package com.rebu.reviewkeyword.entity;

import com.rebu.review.entity.Review;
import jakarta.persistence.*;

@Entity
public class SelectedReviewKeyword {

    @EmbeddedId
    private SelectedReviewKeywordId selectedReviewKeywordId;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("reviewKeywordId")
    @JoinColumn(name = "review_keyword_id")
    private ReviewKeyword reviewKeyword;
}
