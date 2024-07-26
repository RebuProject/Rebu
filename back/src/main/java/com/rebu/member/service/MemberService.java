package com.rebu.member.service;

import com.rebu.member.dto.MemberJoinDto;
import com.rebu.member.entity.Member;
import com.rebu.member.repository.MemberRepository;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(MemberJoinDto memberJoinDto, ProfileGenerateDto profileGenerateDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(memberJoinDto.getPassword());
        Member savedMember = memberRepository.save(memberJoinDto.toEntity(encodedPassword));
        profileService.generateProfile(profileGenerateDto, savedMember);
    }
}
