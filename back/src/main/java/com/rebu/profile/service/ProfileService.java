package com.rebu.profile.service;

import com.rebu.auth.exception.PhoneNotVerifiedException;
import com.rebu.auth.sevice.PhoneAuthService;
import com.rebu.common.service.RedisService;
import com.rebu.common.util.FileUtils;
import com.rebu.member.entity.Member;
import com.rebu.profile.dto.*;
import com.rebu.profile.entity.Profile;
import com.rebu.profile.exception.NicknameDuplicateException;
import com.rebu.profile.exception.PhoneDuplicateException;
import com.rebu.profile.exception.ProfileNotFoundException;
import com.rebu.profile.repository.ProfileRepository;
import com.rebu.security.util.JWTUtil;
import com.rebu.storage.exception.FileUploadFailException;
import com.rebu.storage.service.StorageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final RedisService redisService;
    private final PhoneAuthService phoneAuthService;
    private final StorageService storageService;

    @Transactional
    public void generateProfile(ProfileGenerateDto profileGenerateDto, Member member) {
        profileRepository.save(profileGenerateDto.toEntity(member));
    }

    @Transactional(readOnly = true)
    public ProfileDto getProfileByPhone(String phone) {
        Profile profile = profileRepository.findByPhone(phone)
                .orElseThrow(ProfileNotFoundException::new);

        return ProfileDto.from(profile);
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplicated(String nickname, String purpose) {
        if (profileRepository.findByNickname(nickname).isPresent()) {
            return true;
        }
        redisService.setDataExpire(purpose + ":NicknameCheck:" + nickname, "success", 60 * 15 * 1000L);
        return false;
    }

    @Transactional(readOnly = true)
    public Boolean checkPhoneDuplicated(String phone, String purpose) {
        if (profileRepository.findByPhone(phone).isPresent()) {
            return true;
        }
        redisService.setDataExpire(purpose + ":PhoneCheck:" + phone, "success", 60 * 15 * 1000L);
        return false;
    }

    @Transactional
    public void changeNickname(String oldNickname, ProfileChangeNicknameDto profileChangeNicknameDto, HttpServletResponse response) {

        if (!checkNicknameDuplicatedState("changeNickname", profileChangeNicknameDto.getNickname())) {
            throw new NicknameDuplicateException();
        }

        Profile profile = profileRepository.findByNickname(oldNickname)
                .orElseThrow(ProfileNotFoundException::new);

        profile.changeNickname(profileChangeNicknameDto.getNickname());

        redisService.deleteData("changeNickname:NicknameCheck:" + profileChangeNicknameDto.getNickname());
        redisService.deleteData("Refresh:" + oldNickname);

        resetToken(profileChangeNicknameDto.getNickname(), profile.getType().toString(), response);
    }

    @Transactional
    public void changeIntro(String nickname, ProfileChangeIntroDto profileChangeIntroDto) {
        Profile profile = profileRepository.findByNickname(nickname)
                .orElseThrow(ProfileNotFoundException::new);

        profile.changeIntro(profileChangeIntroDto.getIntroduction());
    }

    @Transactional
    public void changeIsPrivate(String nickname, ProfileChangeIsPrivateDto profileChangeIsPrivateDto) {
        Profile profile = profileRepository.findByNickname(nickname)
                .orElseThrow(ProfileNotFoundException::new);

        profile.changeIsPrivate(profileChangeIsPrivateDto.isPrivate());
    }

    @Transactional
    public void changePhone(String nickname, ProfileChangePhoneDto profileChangePhoneDto) {

        if (!checkPhoneDuplicatedState("changePhone", profileChangePhoneDto.getPhone())) {
            throw new PhoneDuplicateException();
        }

        if (!phoneAuthService.checkPhoneAuthState("changePhone", profileChangePhoneDto.getPhone())) {
            throw new PhoneNotVerifiedException();
        }

        Profile profile = profileRepository.findByNickname(nickname)
                .orElseThrow(ProfileNotFoundException::new);

        profile.changePhone(profileChangePhoneDto.getPhone());

        redisService.deleteData("changePhone:PhoneCheck:" + profileChangePhoneDto.getPhone());
        redisService.deleteData("changePhone:PhoneAuth:" + profileChangePhoneDto.getPhone());
    }

    @Transactional
    public void changePhoto(String nickname, ProfileChangeImgDto profileChangeImgDto) {
        Profile profile = profileRepository.findByNickname(nickname)
                .orElseThrow(ProfileNotFoundException::new);

        MultipartFile file = profileChangeImgDto.getProfileImage();

        String extension = FileUtils.getExtension(file.getOriginalFilename());

        try {
            storageService.uploadFile(profile.getId() + "." + extension , file.getBytes(), "/profiles");
        } catch (IOException e) {
            throw new FileUploadFailException();
        }

    }


    public Boolean checkPhoneDuplicatedState(String purpose, String phone) {
        String key = purpose + ":PhoneCheck:" + phone;
        return redisService.existData(key);
    }

    public Boolean checkNicknameDuplicatedState(String purpose, String nickname) {
        String key = purpose + ":NicknameCheck:" + nickname;
        return redisService.existData(key);
    }

    private void resetToken(String nickname, String type, HttpServletResponse response) {
        String newAccess = JWTUtil.createJWT("access", nickname, type, 600000L);
        String newRefresh = JWTUtil.createJWT("refresh", nickname, type, 86400000L);

        redisService.setDataExpire("Refresh:" + nickname, newRefresh, 86400000L);
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
