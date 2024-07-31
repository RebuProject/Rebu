package com.rebu.member.service;

import com.rebu.common.util.RedisUtils;
import com.rebu.member.dto.MemberJoinDto;
import com.rebu.member.entity.Member;
import com.rebu.member.exception.FindEmailFailException;
import com.rebu.member.exception.MemberNotFoundException;
import com.rebu.member.repository.MemberRepository;
import com.rebu.profile.dto.ProfileDto;
import com.rebu.profile.dto.ProfileGenerateDto;
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

    private static final String KEY = "signup:";

    @Transactional
    public void join(MemberJoinDto memberJoinDto, ProfileGenerateDto profileGenerateDto) {

        if (checkSignupPreAuth(memberJoinDto, profileGenerateDto)) {

            String encodedPassword = bCryptPasswordEncoder.encode(memberJoinDto.getPassword());
            Member savedMember = memberRepository.save(memberJoinDto.toEntity(encodedPassword));
            profileService.generateProfile(profileGenerateDto, savedMember);
        }
    }

    @Transactional
    public Boolean checkEmailDuplicated(String email, String purpose) {

       if (memberRepository.findByEmail(email).isPresent()) {
           return true;
       }
       redisUtils.setDataExpire(purpose + ":CheckEmail:" + email, "success", 60 * 15 * 1000L);
       return false;
    }

    @Transactional
    public void changePassword(String email, String password) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.changePassword(password);
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
        Boolean emailCheck = redisUtils.existData(KEY + "CheckEmail:" + memberJoinDto.getEmail());
        Boolean phoneAuth = redisUtils.existData(KEY + "PhoneAuth:" + profileGenerateDto.getPhone());
        Boolean emailAuth = redisUtils.existData(KEY + "EmailAuth:" + memberJoinDto.getEmail());
        // 닉네임 중복 검사
        // 전화번호 중복 검사
        return true;
    }

}
