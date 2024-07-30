package com.rebu.auth.test;

import com.rebu.auth.sevice.MailAuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MailAuthServiceTest {

    @Autowired
    private MailAuthService mailAuthService;

    @Transactional
    @DisplayName("이메일 인증 번호 전송")
    @Test
    @Order(1)
    public void emailAuthSendTest() {
        // given
        String email = "zx8571@naver.com";
        String purpose = "signup";

        // when
        mailAuthService.sendMail(email, purpose);
    }

    @Transactional
    @DisplayName("이메일 인증 번호 확인")
    @Test
    @Order(2)
    public void emailAuthentication() {

        // given
        String email = "zx8571@naver.com";
        String code = "abcdefg";

        // when
        Boolean flag = mailAuthService.verifyEmailCode(email, code);

        // then 이메일 인증 번호 전송이 선행되어야함
        Assertions.assertThat(flag).isFalse();
    }

}
