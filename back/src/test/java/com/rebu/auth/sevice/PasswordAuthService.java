package com.rebu.auth.sevice;

import com.rebu.auth.dto.AuthDto;
import com.rebu.auth.dto.PasswordAuthDto;
import com.rebu.auth.exception.AuthPurposeInvalidException;
import com.rebu.auth.exception.PasswordAutFailException;
import com.rebu.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PasswordAuthService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final List<String> acceptedPurposes = new ArrayList<>(Arrays.asList("withdrawal", "profile-delete"));


    @Transactional
    public boolean passwordAuthenticate(AuthDto authDto, PasswordAuthDto passwordAuthDto) {
        for (String acceptedPurpose : acceptedPurposes) {
            if (passwordAuthDto.getPurpose().equals(acceptedPurpose)) {
                if (bCryptPasswordEncoder.matches(passwordAuthDto.getPassword(), authDto.getPassword())) {
                    return true;
                }
                throw new PasswordAutFailException();
            }
        }
        throw new AuthPurposeInvalidException();
    }
}
