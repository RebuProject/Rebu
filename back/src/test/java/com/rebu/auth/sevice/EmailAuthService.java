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
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }" +
                ".email-container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 3px rgba(0,0,0,0.1); }" +
                ".header { background-color: #4CAF50; padding: 20px; border-top-left-radius: 8px; border-top-right-radius: 8px; text-align: center; color: white; }" +
                ".header h1 { margin: 0; font-size: 24px; }" +
                ".content { padding: 20px; text-align: center; }" +
                ".content p { font-size: 18px; color: #333333; }" +
                ".content .code { font-size: 24px; font-weight: bold; color: #4CAF50; }" +
                ".footer { background-color: #f4f4f4; padding: 10px; text-align: center; font-size: 12px; color: #888888; border-bottom-left-radius: 8px; border-bottom-right-radius: 8px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='email-container'>" +
                "<div class='header'>" +
                "<h1>REBU</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>안녕하세요,</p>" +
                "<p>아래 인증번호를 입력하여 인증을 완료해주세요:</p>" +
                "<p class='code'>" + code + "</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>본 이메일은 발신 전용입니다. 문의사항이 있으시면 지원팀에 연락주세요.</p>" +
                "</div>" +
                "</div>" +
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
