package com.rebu.reviewkeyword.repository;

import com.rebu.reviewkeyword.dto.ReviewKeywordCountDto;
import com.rebu.reviewkeyword.entity.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

    /**
     * TODO. 리뷰 키워드 카운트
     * 소유자 닉네임으로 소유자 프로필 식별자를 가져옴
     * 프로필 식별자로 게시물 타입이 리뷰인 것들 다가져옴
     * 게시물 식별자로 리뷰 키워드 식별자를 모두 가져와서
     * 그 리뷰 키워드 식별자를 집계한 후, 리뷰 키워드 테이블과 조인
     */
    List<ReviewKeywordCountDto> countReviewKeywordsByProfileId(Long profileId);
}
