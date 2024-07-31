package com.rebu.feed.service;


import com.rebu.common.aop.annotation.Authorized;
import com.rebu.feed.dto.FeedCreateByEmployeeDto;
import com.rebu.feed.dto.FeedCreateByShopDto;
import com.rebu.feed.dto.FeedDeleteDto;
import com.rebu.feed.dto.FeedModifyDto;
import com.rebu.feed.entity.Feed;
import com.rebu.feed.exception.FeedNotFoundException;
import com.rebu.feed.repository.FeedRepository;
import com.rebu.profile.employee.entity.EmployeeProfile;
import com.rebu.profile.employee.repository.EmployeeProfileRepository;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.enums.Type;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.exception.ProfileUnauthorizedException;
import com.rebu.profile.repository.ProfileRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageService feedImageService;
    private final ProfileRepository profileRepository;
    private final EmployeeProfileRepository employeeProfileRepository;

    @Transactional
    @Authorized(allowed = Type.EMPLOYEE)
    public void createByEmployee(@Valid FeedCreateByEmployeeDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        List<String> regSelects = dto.getRegSelects();

        if(regSelects.contains("EMPLOYEE")){
            Feed feed = feedRepository.save(dto.toEntity(profile, profile, Feed.Type.NONE));
            feedImageService.createFeedImages(dto.getImages(), feed);
        }

        if(regSelects.contains("SHOP")){
            EmployeeProfile employeeProfile= employeeProfileRepository.findById(profile.getId()).orElseThrow(ProfileNotFoundException::new);
            Profile shop = employeeProfile.getShop();
            if(shop == null)
                throw new ProfileUnauthorizedException();
            Feed feed = feedRepository.save(dto.toEntity(profile, employeeProfile.getShop(), Feed.Type.NONE));
            feedImageService.createFeedImages(dto.getImages(), feed);
        }
    }

    @Transactional
    @Authorized(allowed = Type.SHOP)
    public void createByShop(@Valid FeedCreateByShopDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Feed feed = feedRepository.save(dto.toEntity(profile));
        feedImageService.createFeedImages(dto.getImages(), feed);
    }

    @Transactional
    @Authorized(allowed = {Type.EMPLOYEE, Type.SHOP})
    public void modify(@Valid FeedModifyDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Feed feed = feedRepository.findById(dto.getFeedId()).orElseThrow(FeedNotFoundException::new);
        checkPermission(profile, feed);
        feedImageService.deleteFeedImages(feed.getId());
        feedImageService.createFeedImages(dto.getImages(), feed);
        feed.changeContent(dto.getContent());
    }

    @Transactional
    @Authorized(allowed = {Type.EMPLOYEE, Type.SHOP})
    public void delete(@Valid FeedDeleteDto dto) {
        Profile profile = profileRepository.findByNickname(dto.getNickname()).orElseThrow(ProfileNotFoundException::new);
        Feed feed = feedRepository.findById(dto.getFeedId()).orElseThrow(FeedNotFoundException::new);
        checkPermission(profile, feed);
        feedImageService.deleteFeedImages(feed.getId());
        feedRepository.delete(feed);
    }

    private void checkPermission(Profile profile, Feed feed) {
        Profile owner = feed.getOwner();
        if(profile.getType() == Type.SHOP && !profile.equals(owner))
            throw new ProfileUnauthorizedException();
        if(profile.getType() == Type.EMPLOYEE && !profile.equals(owner)){
            List<EmployeeProfile> employees = employeeProfileRepository.findByShopId(owner.getId());
            if (!employees.contains(profile))
                throw new ProfileUnauthorizedException();
        }
    }
}
