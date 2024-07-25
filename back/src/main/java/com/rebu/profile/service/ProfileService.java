package com.rebu.profile.service;

import com.rebu.member.entity.Member;
import com.rebu.profile.dto.ProfileGenerateDto;

public interface ProfileService {
    void generateProfile(ProfileGenerateDto profileGenerateDto, Member member);
}
