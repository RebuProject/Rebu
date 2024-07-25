package com.rebu.member.service;

import com.rebu.member.dto.MemberJoinDto;
import com.rebu.member.entity.Member;

public interface MemberService {
    Member join(MemberJoinDto memberJoinDto);
}
