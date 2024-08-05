package com.rebu.profile.shop.exception;

import com.rebu.common.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShopProfileExceptionConstants implements ExceptionConstants {

    CATEGORY_MISMATCH("카테고리 형식 불일치"),
    LICENSE_NUM_NOT_VERIFIED("사업자 등록번호 미인증");

    final String code;
}
