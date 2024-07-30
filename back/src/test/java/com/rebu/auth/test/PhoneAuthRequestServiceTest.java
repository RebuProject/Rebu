package com.rebu.auth.test;

import com.rebu.auth.controller.dto.PhoneAuthRequest;
import com.rebu.auth.sevice.PhoneAuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PhoneAuthRequestServiceTest {
    @Autowired
    private PhoneAuthService phoneAuthService;

    @Transactional
    @DisplayName("휴대폰 인증 전송")
    @Test
    public void phoneAuthSendTest() {
        // given
        String phone = "010-8591-4442";

        // when
        phoneAuthService.sendMessage(phone);

        // then
    }

    @Transactional
    @DisplayName("휴대폰 인증 번호 확인")
    @Test
    public void phoneAuthentication() {
        // given
        PhoneAuthRequest phoneAuthRequest = new PhoneAuthRequest("010-8591-4442", "abcdef");

        // when
        Boolean flag = phoneAuthService.verifyCode(phoneAuthRequest.toDto());

        // then
        Assertions.assertThat(flag).isFalse();
    }

}
