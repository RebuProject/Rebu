package com.rebu.auth.sevice;

import com.rebu.common.util.RedisUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;
    private static final String senderEmail = "w01085914442@gmail.com";

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

    private String setContext(String code) {
        return "<html>" +
                "<body>" +
                "<h1>REBU</h1>" +
                "<p>인증번호는 <strong>" + code + "</strong> 입니다.</p>" +
                "</body>" +
                "</html>";
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {
        String authCode = createCode();
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("REBU 인증번호");
        message.setFrom(senderEmail);
        message.setText(setContext(authCode), "utf-8", "html");

        redisUtils.setDataExpire(email, authCode, 60 * 30L);
        return message;
    }

    public void sendMail(String toEmail) throws MessagingException {
        if (redisUtils.existData(toEmail)) {
            redisUtils.deleteData(toEmail);
        }
        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtils.getData(email);
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(code);
    }
}
