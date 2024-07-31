package com.rebu.auth.sevice;

import com.rebu.auth.dto.PasswordSendDto;
import com.rebu.auth.exception.PasswordAutFailException;
import com.rebu.security.dto.AuthProfileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordAuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void passwordAuthenticate(AuthProfileInfo authDto, PasswordSendDto passwordSendDto) {

        if (!bCryptPasswordEncoder.matches(passwordSendDto.getPassword(), authDto.getPassword())) {
            throw new PasswordAutFailException();
        }
    }
}
