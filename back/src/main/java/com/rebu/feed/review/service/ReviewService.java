package com.rebu.feed.review.service;

import com.rebu.common.aop.annotation.Authorized;
import com.rebu.common.util.ListUtils;
import com.rebu.feed.dto.*;
import com.rebu.feed.entity.Feed;
import com.rebu.feed.exception.FeedNotFoundException;
import com.rebu.feed.review.dto.*;
import com.rebu.feed.review.entity.Review;
import com.rebu.feed.review.exception.ReviewNotAllowedException;
import com.rebu.feed.review.repository.ReviewRepository;
import com.rebu.feed.service.FeedImageService;
import com.rebu.feed.service.HashtagService;
import com.rebu.like.entity.LikeFeed;
import com.rebu.like.repository.LikeFeedRepository;
import com.rebu.profile.dto.ProfileDto;
import com.rebu.profile.employee.entity.EmployeeProfile;
import com.rebu.profile.employee.repository.EmployeeProfileRepository;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.exception.ProfileUnauthorizedException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.profile.shop.dto.ShopProfileDto;
import com.rebu.profile.shop.entity.ShopProfile;
import com.rebu.profile.shop.repository.ShopProfileRepository;
import com.rebu.reservation.entity.Reservation;
import com.rebu.reservation.exception.ReservationNotFoundException;
import com.rebu.reservation.repository.ReservationRepository;
import com.rebu.reviewkeyword.dto.ReviewKeywordDto;
import com.rebu.reviewkeyword.entity.ReviewKeyword;
import com.rebu.reviewkeyword.entity.SelectedReviewKeyword;
import com.rebu.reviewkeyword.entity.SelectedReviewKeywordId;
import com.rebu.reviewkeyword.repository.ReviewKeywordRepository;
import com.rebu.reviewkeyword.repository.SelectedReviewKeywordRepository;
import com.rebu.scrap.entity.Scrap;
import com.rebu.scrap.repository.ScrapRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ProfileRepository profileRepository;
    private final HashtagService hashtagService;
    private final ReviewRepository reviewRepository;
    private final EmployeeProfileRepository employeeProfileRepository;
    private final ReservationRepository reservationRepository;
    private final FeedImageService feedImageService;
    private final SelectedReviewKeywordRepository selectedReviewKeywordRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ScrapRepository scrapRepository;
    private final LikeFeedRepository likeFeedRepository;
    private final ShopProfileRepository shopProfileRepository;

    /**
     * ReviewService :: readReviewByProfile method
     * 일반 프로필이 작성한 리뷰를 조회
     * @param dto 조회할 리뷰 정보
     */
    @Transactional(readOnly = true)
    public List<ReviewByProfileDto> readReviewByProfile(ReviewReadByProfileDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getProfileNickname()).orElseThrow(ProfileNotFoundException::new);
        Profile searchProfile = profileRepository.findByNickname(dto.getSearchProfileNickname()).orElseThrow(ProfileNotFoundException::new);

        List<Review> reviews = reviewRepository.findByProfileAndType(searchProfile, Feed.Type.REVIEW);
        List<Feed> feeds = ListUtils.applyFunctionToElements(reviews, review -> (Feed)review);
        List<Scrap> scraps = scrapRepository.findByProfileAndFeedIn(profile, feeds);
        List<LikeFeed> likeFeeds = likeFeedRepository.findByProfileAndFeedIn(profile, feeds);

        Map<Long, ReviewByProfileDto> map = new LinkedHashMap<>();

        for(Review review : reviews)
            map.put(review.getId(), ReviewByProfileDto.from(review));

        for(Scrap scrap : scraps)
            map.get(scrap.getFeed().getId()).setIsScraped(true);

        for(LikeFeed likeFeed : likeFeeds)
            map.get(likeFeed.getFeed().getId()).setIsLiked(true);

        return map.values().stream().toList();
    }

    /**
     * ReviewService :: readReviewToEmployee method
     * 직원 프로필에 작성된 리뷰를 조회
     * @param dto 조회할 리뷰 정보
     */
    @Transactional(readOnly = true)
    public List<ReviewToEmployeeDto> readReviewToEmployee(ReviewReadToEmployeeDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getProfileNickname()).orElseThrow(ProfileNotFoundException::new);
        EmployeeProfile employee = employeeProfileRepository.findByNickname(dto.getEmployeeNickname()).orElseThrow(ProfileNotFoundException::new);

        List<Review> reviews = reviewRepository.findByEmployeeProfileAndType(employee, Feed.Type.REVIEW);
        List<Feed> feeds = ListUtils.applyFunctionToElements(reviews, review -> (Feed)review);
        List<Scrap> scraps = scrapRepository.findByProfileAndFeedIn(profile, feeds);
        List<LikeFeed> likeFeeds = likeFeedRepository.findByProfileAndFeedIn(profile, feeds);

        Map<Long, ReviewToEmployeeDto> map = new LinkedHashMap<>();

        for(Review review : reviews)
            map.put(review.getId(), ReviewToEmployeeDto.from(review));

        for(Scrap scrap : scraps)
            map.get(scrap.getFeed().getId()).setIsScraped(true);

        for(LikeFeed likeFeed : likeFeeds)
            map.get(likeFeed.getFeed().getId()).setIsLiked(true);

        return map.values().stream().toList();
    }

    /**
     * ReviewService :: readReviewToShop method
     * 매장 프로필에 작성된 리뷰를 조회
     * @param dto 조회할 리뷰 정보
     */
    @Transactional(readOnly = true)
    public List<ReviewToShopDto> readReviewToShop(ReviewReadToShopDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getProfileNickname()).orElseThrow(ProfileNotFoundException::new);
        ShopProfile shop = shopProfileRepository.findByNickname(dto.getShopNickname()).orElseThrow(ProfileNotFoundException::new);

        List<Review> reviews = reviewRepository.findByShopProfileAndType(shop, Feed.Type.REVIEW);
        List<Feed> feeds = ListUtils.applyFunctionToElements(reviews, review -> (Feed)review);
        List<Scrap> scraps = scrapRepository.findByProfileAndFeedIn(profile, feeds);
        List<LikeFeed> likeFeeds = likeFeedRepository.findByProfileAndFeedIn(profile, feeds);

        Map<Long, ReviewToShopDto> map = new LinkedHashMap<>();

        for(Review review : reviews)
            map.put(review.getId(), ReviewToShopDto.from(review));

        for(Scrap scrap : scraps)
            map.get(scrap.getFeed().getId()).setIsScraped(true);

        for(LikeFeed likeFeed : likeFeeds)
            map.get(likeFeed.getFeed().getId()).setIsLiked(true);

        return map.values().stream().toList();
    }

    /**
     * ReviewService :: create method
     * 리뷰 정보를 받아 리뷰를 작성
     * @param dto 작성할 피드 정보
     */
    @Transactional
    @Authorized(allowed = {Type.COMMON})
    public void create(@Valid ReviewCreateDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Reservation reservation = reservationRepository.findById(dto.getReservationId()).orElseThrow(ReservationNotFoundException::new);

        if(!profile.equals(reservation.getProfile()))
            throw new ProfileUnauthorizedException();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = reservation.getStartDateTime();

        if(now.isBefore(startDateTime) || now.isAfter(startDateTime.plusDays(7)))
            throw new ReviewNotAllowedException();

        EmployeeProfile employee = reservation.getEmployeeProfile();
        ShopProfile shop = reservation.getShopProfile();
        Review review = reviewRepository.save(dto.toEntity(profile, employee, shop, reservation));
        List<SelectedReviewKeyword> selectedReviewKeywords = generateSelectedReviewKeyword(dto.getReviewKeywordIds(), review);
        selectedReviewKeywordRepository.saveAll(selectedReviewKeywords);
        hashtagService.createHashTags(dto.getHashtags(), review);
        feedImageService.createFeedImages(dto.getImages(), review);
    }

    /**
     * ReviewService :: modify method
     * 수정할 리뷰 정보를 받아 리뷰를 수정
     * @param dto 수정할 피드 정보
     */
    @Transactional
    @Authorized(allowed = {Type.COMMON})
    public void modify(@Valid ReviewModifyDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Review review = reviewRepository.findById(dto.getFeedId()).orElseThrow(FeedNotFoundException::new);
        Reservation reservation = review.getReservation();

        if(!profile.equals(review.getWriter()))
            throw new ProfileUnauthorizedException();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = reservation.getStartDateTime();

        if(now.isBefore(startDateTime) || now.isAfter(startDateTime.plusDays(7)))
            throw new ReviewNotAllowedException();

        selectedReviewKeywordRepository.deleteByReviewId(review.getId());
        List<SelectedReviewKeyword> selectedReviewKeywords = generateSelectedReviewKeyword(dto.getReviewKeywordIds(), review);
        selectedReviewKeywordRepository.saveAll(selectedReviewKeywords);
        hashtagService.deleteHashTags(review.getId());
        hashtagService.createHashTags(dto.getHashtags(), review);
        feedImageService.deleteFeedImages(review.getId());
        feedImageService.createFeedImages(dto.getImages(), review);

        review.changeContent(dto.getContent());
        review.changeRating(dto.getRating());
    }

    /**
     * ReviewService :: delete method
     * 특정 리뷰를 삭제
     * @param dto 삭제할 리뷰 정보
     */
    @Transactional
    @Authorized(allowed = {Type.COMMON})
    public void delete(@Valid ReviewDeleteDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Review review = reviewRepository.findById(dto.getFeedId()).orElseThrow(FeedNotFoundException::new);

        if(!profile.equals(review.getWriter()))
            throw new ProfileUnauthorizedException();

        selectedReviewKeywordRepository.deleteByReviewId(review.getId());
        feedImageService.deleteFeedImages(review.getId());
        hashtagService.deleteHashTags(review.getId());
        reviewRepository.delete(review);
    }

    private List<SelectedReviewKeyword> generateSelectedReviewKeyword(List<Long> reviewKeywordIds, Review review) {
        List<SelectedReviewKeyword> result = new ArrayList<>();
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.findAllById(reviewKeywordIds);
        for (ReviewKeyword reviewKeyword : reviewKeywords) {
            result.add(SelectedReviewKeyword.builder()
                    .selectedReviewKeywordId(new SelectedReviewKeywordId(review.getId(), reviewKeyword.getId()))
                    .review(review)
                    .reviewKeyword(reviewKeyword).build());
        }
        return result;
    }
}
