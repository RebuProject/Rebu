package com.rebu.auth.sevice;

import com.rebu.auth.dto.PasswordSendDto;
import com.rebu.auth.exception.PasswordAutFailException;
import com.rebu.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordAuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisService redisService;

    public void verifyPassword(PasswordSendDto passwordSendDto) {

        if (!bCryptPasswordEncoder.matches(passwordSendDto.getReceivePassword(), passwordSendDto.getPassword())) {
            throw new PasswordAutFailException();
        }
        redisService.setDataExpire(generateForAuthKey(passwordSendDto), "success", 60 * 5 * 1000L);

    }

    public Boolean checkPasswordAuthState(String purpose, String nickname) {
        String key = purpose + ":PasswordAuth:" + nickname;
        return redisService.existData(key);
    }

    private String generateForAuthKey(PasswordSendDto passwordSendDto) {
        return passwordSendDto.getPurpose() + ":PasswordAuth:" + passwordSendDto.getNickname();
    }

}
