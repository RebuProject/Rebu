package com.rebu.member.service;

import com.rebu.common.util.RedisUtils;
import com.rebu.member.dto.MemberJoinDto;
import com.rebu.member.dto.MemberChangePasswordDto;
import com.rebu.member.entity.Member;
import com.rebu.member.exception.*;
import com.rebu.member.repository.MemberRepository;
import com.rebu.profile.dto.ProfileDto;
import com.rebu.profile.dto.ProfileGenerateDto;
import com.rebu.profile.exception.NicknameDuplicateException;
import com.rebu.profile.exception.PhoneDuplicateException;
import com.rebu.profile.exception.PhoneNotVerifiedException;
import com.rebu.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileService profileService;
    private final RedisUtils redisUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String PURPOSE = "signup:";

    @Transactional
    public void join(MemberJoinDto memberJoinDto, ProfileGenerateDto profileGenerateDto) {

        if (checkSignupPreAuth(memberJoinDto, profileGenerateDto)) {
            String encodedPassword = bCryptPasswordEncoder.encode(memberJoinDto.getPassword());
            Member savedMember = memberRepository.save(memberJoinDto.toEntity(encodedPassword));
            profileService.generateProfile(profileGenerateDto, savedMember);
        }
        deleteAuthInfo(memberJoinDto, profileGenerateDto);
    }

    @Transactional
    public Boolean checkEmailDuplicated(String email, String purpose) {

       if (memberRepository.findByEmail(email).isPresent()) {
           return true;
       }
       redisUtils.setDataExpire(purpose + ":MailCheck:" + email, "success", 60 * 15 * 1000L);
       return false;
    }

    @Transactional
    public void changePassword(String email, MemberChangePasswordDto memberChangePasswordDto) {

        if (!redisUtils.existData("changePassword:MailAuth:" +email)) {
            throw new EmailNotVerifiedException();
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.changePassword(bCryptPasswordEncoder.encode(memberChangePasswordDto.getPassword()));

        redisUtils.deleteData("changePassword:MailAuth:" +email);
    }

    @Transactional
    public String getEmail(String name, String phone) {

        ProfileDto profileDto = profileService.getProfileByPhone(phone);
        Member member = memberRepository.findById(profileDto.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        if (!member.getName().equals(name)) {
            throw new FindEmailFailException();
        }

        return member.getEmail();
    }

    private Boolean checkSignupPreAuth(MemberJoinDto memberJoinDto, ProfileGenerateDto profileGenerateDto) {
        boolean emailCheck = redisUtils.existData(PURPOSE + "MailCheck:" + memberJoinDto.getEmail());
        boolean emailAuth = redisUtils.existData(PURPOSE + "MailAuth:" + memberJoinDto.getEmail());
        boolean phoneCheck = redisUtils.existData(PURPOSE + "PhoneCheck:" + profileGenerateDto.getPhone());
        boolean phoneAuth = redisUtils.existData(PURPOSE + "PhoneAuth:" + profileGenerateDto.getPhone());
        boolean nicknameCheck = redisUtils.existData(PURPOSE + "NicknameCheck:" + profileGenerateDto.getNickname());

        if (!emailCheck) {
            throw new EmailDuplicateException();
        }

        if (!emailAuth) {
            throw new EmailNotVerifiedException();
        }

        if (!phoneCheck) {
            throw new PhoneDuplicateException();
        }

        if (!phoneAuth) {
            throw new PhoneNotVerifiedException();
        }

        if (!nicknameCheck) {
            throw new NicknameDuplicateException();
        }

        return true;
    }

    private void deleteAuthInfo(MemberJoinDto memberJoinDto, ProfileGenerateDto profileGenerateDto) {
        redisUtils.deleteData(PURPOSE + "MailCheck:" + memberJoinDto.getEmail());
        redisUtils.deleteData(PURPOSE + "MailAuth:" + memberJoinDto.getEmail());
        redisUtils.deleteData(PURPOSE + "PhoneCheck:" + profileGenerateDto.getPhone());
        redisUtils.deleteData(PURPOSE + "PhoneAuth:" + profileGenerateDto.getPhone());
        redisUtils.deleteData(PURPOSE + "NicknameCheck:" + profileGenerateDto.getNickname());
    }

}
