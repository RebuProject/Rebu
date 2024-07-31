package com.rebu.profile.service;

import com.rebu.common.util.RedisUtils;
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
    private final RedisUtils redisUtils;

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

    @Transactional
    public Boolean checkNicknameDuplicated(String nickname, String purpose) {
        if (profileRepository.findByNickname(nickname).isPresent()) {
            return true;
        }
        redisUtils.setDataExpire(purpose + ":NicknameCheck:" + nickname, "success", 60 * 15 * 1000L);
        return false;
    }

    @Transactional
    public Boolean checkPhoneDuplicated(String phone, String purpose) {
        if (profileRepository.findByPhone(phone).isPresent()) {
            return true;
        }
        redisUtils.setDataExpire(purpose + ":PhoneCheck:" + phone, "success", 60 * 15 * 1000L);
        return false;
    }
}
