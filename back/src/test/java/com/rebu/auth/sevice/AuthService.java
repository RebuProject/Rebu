package com.rebu.auth.sevice;

import com.rebu.auth.dto.PasswordAuthDto;
import com.rebu.auth.exception.AuthPurposeInvalidException;
import com.rebu.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService {

    private final List<String> acceptedPurposes = new ArrayList<>(Arrays.asList("withdrawal", "profile-delete"));

    @Autowired
    private MemberService memberService;

    @Transactional
    public void passwordAuthentication(PasswordAuthDto passwordAuthDto) {
        for (String acceptedPurpose : acceptedPurposes) {
            if (passwordAuthDto.getPurpose().equals(acceptedPurpose)) {
                // 비번 인증 로직 실행

            }
        }
        throw new AuthPurposeInvalidException();
    }
}
