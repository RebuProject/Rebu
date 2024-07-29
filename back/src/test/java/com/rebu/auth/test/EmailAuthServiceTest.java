package com.rebu.auth.test;

import com.rebu.auth.sevice.EmailAuthService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class EmailAuthServiceTest {

    @Autowired
    private EmailAuthService emailAuthService;

    @Transactional
    @DisplayName("이메일 인증")
    @Test
    public void emailAuthServiceTest() throws MessagingException {
        // given
        String email = "zx8571@naver.com";

        // when
        emailAuthService.sendMail(email);
    }

}
