package com.rebu.auth.test;

import com.rebu.auth.dto.LicenseNumAuthResult;
import com.rebu.auth.dto.LicenseNumSendDto;
import com.rebu.auth.sevice.LicenseNumAuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LicenseNumAuthServiceTest {
    @Autowired
    private LicenseNumAuthService licenseNumAuthService;

    @DisplayName("사업자 번호 인증")
    @Test
    public void LicenceNumAuthServiceTest() {
        // given
        LicenseNumSendDto licenseNumSendDto = new LicenseNumSendDto("2208162517");

        // when
        LicenseNumAuthResult result = licenseNumAuthService.verifyLicenceNum(licenseNumSendDto);

        // then
        assertThat(result.getShopName()).isEqualTo("피닉스");
    }
}
