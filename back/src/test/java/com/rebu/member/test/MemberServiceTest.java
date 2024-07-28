package com.rebu.member.test;

import com.rebu.member.entity.Member;
import com.rebu.member.enums.Gender;
import com.rebu.member.repository.MemberRepository;
import com.rebu.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @DisplayName("MemberServiceTest Start")
    @BeforeEach
    public void setUp() {
        System.out.println("MemberServiceTest Start");
        Member member = Member.builder()
                .email("rebu@naver.com")
                .password("abcd1234@")
                .name("원승현")
                .gender(Gender.MALE)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    @DisplayName("사용자 이메일 중복 확인")
    @Test
    public void emailDuplicateTest() {
        // given
        String email = "rebu@naver.com";

        // when
        Boolean isDuplicated = memberService.checkEmail(email);

        // then
        assertThat(isDuplicated).isTrue();
    }

    @Transactional
    @DisplayName("사용자 비밀번호 변경")
    @Test
    public void changePasswordTest() {
        // given
        String email = "rebu@naver.com";
        String newPassword = "qwerty12345@";

        // when
        memberService.changePassword(email, newPassword);

        // then
        Member member = memberRepository.findByEmail(email).get();
        assertThat(member.getPassword()).isEqualTo(newPassword);
    }

    @Transactional
    @DisplayName("사용자 이메일 찾기")
    @Test
    public void findEmail() {
        // given
        String name = "원승현";
        String phone = "010-0000-0000";

        // when

        // then
    }

}
