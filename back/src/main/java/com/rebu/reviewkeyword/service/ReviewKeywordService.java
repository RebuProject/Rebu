package com.rebu.reviewkeyword.service;

import com.rebu.common.util.ListUtils;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.exception.ProfileUnauthorizedException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.reviewkeyword.dto.ReviewKeywordCountDto;
import com.rebu.reviewkeyword.dto.ReviewKeywordDto;
import com.rebu.reviewkeyword.entity.ReviewKeyword;
import com.rebu.reviewkeyword.exception.ReviewKeywordNotFoundException;
import com.rebu.reviewkeyword.repository.ReviewKeywordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewKeywordService {

    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public List<ReviewKeywordDto> readAll() {
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findAll();
        return ListUtils.applyFunctionToElements(reviewKeywords, ReviewKeywordDto::from);
    }

    @Transactional
    public void create(String keyword){
        reviewKeywordRepository.save(ReviewKeyword.builder().keyword(keyword).build());
    }

    @Transactional
    public void update(Long reviewKeywordId, String keyword){
        ReviewKeyword reviewKeyword = reviewKeywordRepository.findById(reviewKeywordId).orElseThrow(ReviewKeywordNotFoundException::new);
        reviewKeyword.changeKeyword(keyword);
    }

    @Transactional
    public void delete(Long reviewKeywordId){
        reviewKeywordRepository.deleteById(reviewKeywordId);
    }

    @Transactional(readOnly = true)
    public List<ReviewKeywordCountDto> countByNickname(String nickname) {
        Profile profile = profileRepository.findByNickname(nickname).orElseThrow(ProfileNotFoundException::new);
        if(profile.getType() == Type.COMMON)
            throw new ProfileUnauthorizedException();
        return reviewKeywordRepository.countReviewKeywordsByProfileId(profile.getId());
    }
}