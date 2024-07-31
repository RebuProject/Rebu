package com.rebu.profile.service;

import com.rebu.member.entity.Member;
import com.rebu.profile.dto.ProfileDto;
import com.rebu.profile.dto.ProfileGenerateDto;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public void generateProfile(ProfileGenerateDto profileGenerateDto, Member member) {
        profileRepository.save(profileGenerateDto.toEntity(member));
    }

    @Transactional
    public ProfileDto getProfileByPhone(String phone) {
        Profile profile = profileRepository.findByPhone(phone)
                .orElseThrow(ProfileNotFoundException::new);

        return ProfileDto.from(profile);
    }
}
