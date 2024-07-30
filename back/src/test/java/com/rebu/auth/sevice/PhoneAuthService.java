package com.rebu.auth.sevice;

import com.rebu.auth.controller.dto.PhoneAuthRequest;
import com.rebu.auth.dto.PhoneAuthDto;
import com.rebu.auth.exception.PhoneCodeMismatchException;
import com.rebu.auth.exception.PhoneSessionNotFoundException;
import com.rebu.common.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PhoneAuthService {

    private final DefaultMessageService messageService;
    private final RedisUtils redisUtils;

    private static final String PREFIX = "PhoneAuth:";
    private static final String senderPhone = "01085914442";

    private String createCode() {
        int leftLimit = 48; // number '0'
        int rightLimit = 122; // alphabet 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void sendMessage(String phone) {
        if (redisUtils.existData(generatePrefixedKey(phone))) {
            redisUtils.deleteData(generatePrefixedKey(phone));
        }
        String authCode = createCode();
        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(phone);
        message.setText(authCode);
        redisUtils.setDataExpire(generatePrefixedKey(phone), authCode, 60 * 5 * 1000L);

        messageService.sendOne(new SingleMessageSendingRequest(message));
    }

    public Boolean verifyCode(PhoneAuthDto phoneAuthDto) {
        String issuedCode = redisUtils.getData(generatePrefixedKey(phoneAuthDto.getPhone()));
        if (issuedCode == null) {
            throw new PhoneSessionNotFoundException();
        }

        if (!issuedCode.equals(phoneAuthDto.getVerifyCode())) {
            throw new PhoneCodeMismatchException();
        }
        return true;
    }

    private String generatePrefixedKey(String key) {
        return PREFIX + key;
    }
}
